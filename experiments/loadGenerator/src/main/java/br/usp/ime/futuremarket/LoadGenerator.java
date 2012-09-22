package br.usp.ime.futuremarket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.usp.ime.futuremarket.choreography.Portal;
import br.usp.ime.futuremarket.choreography.PortalLoaderImplChor;
import br.usp.ime.futuremarket.orchestration.PortalLoaderImplOrch;

public class LoadGenerator implements Runnable {
    private static final int THREADS_TIMEOUT = 360;

    private static FrequencyHelper freqHelper;
    private static List<Portal> portals;
    private static Integer failures = 0;
    private static Integer requests = 0;

    private static final ShopList SHOPLIST = getShopList();

    private static final String LOWEST_PRICE_LOG = "lowest_price.log";
    private static final String PURCHASE_LOG = "purchase.log";
    private static final String SHIPMENT_LOG = "shipment.log";

    private static BufferedWriter lowestPriceLog;
    private static BufferedWriter purchaseLog;
    private static BufferedWriter shipmentLog;

    private static String archType;
    // Requests per minute
    private static int initialFreq;
    private static int endingFreq;
    private static int maxThreads;

    private Portal portal;
    private final int threadNumber;

    public static void main(final String[] args) throws IOException {
        readArgs(args);
        openLogs();
        setFutureMarket();
        runSimulations();
        closeLogs();
    }

    private static void readArgs(final String args[]) {
        archType = args[0];
        // Requests per minute
        initialFreq = Integer.parseInt(args[1]);
        endingFreq = Integer.parseInt(args[2]);
        maxThreads = Integer.parseInt(args[3]);
    }

    private static void setFutureMarket() throws IOException {
        PortalLoader loader;

        if ("orch".equals(archType)) {
            loader = new PortalLoaderImplOrch();
        } else {
            loader = new PortalLoaderImplChor();
        }

        portals = loader.getPortals();
    }

    public LoadGenerator(final int threadNumber) {
        this.threadNumber = threadNumber;
        System.out.println("# INFO Thread " + threadNumber + " has started.");
        portal = portals.get(threadNumber % portals.size());
    }

    private static ShopList getShopList() {
        final ShopList list = new ShopList();
        String product;
        int quantity;
        ShopListItem item;

        for (int i = 1; i < 11; i++) {
            product = "product" + i;
            quantity = i;
            item = getShopListItem(product, quantity);
            list.put(item);
        }

        return list;
    }

    private static ShopListItem getShopListItem(final String productName, final int quantity) {
        final Product product = new Product(productName);
        final ShopListItem item = new ShopListItem(product);
        item.setQuantity(quantity);

        return item;
    }

    private static void openLogs() {
        lowestPriceLog = openLog(LOWEST_PRICE_LOG);
        purchaseLog = openLog(PURCHASE_LOG);
        shipmentLog = openLog(SHIPMENT_LOG);
    }

    private static void closeLogs() {
        try {
            lowestPriceLog.close();
            purchaseLog.close();
            shipmentLog.close();
        } catch (IOException e) {
            System.err.println("# ERROR closing log file.");
            e.printStackTrace();
        }
    }

    private static BufferedWriter openLog(final String filename) {
        FileWriter fstream = null;

        try {
            fstream = new FileWriter(filename);
        } catch (IOException e) {
            System.err.println("# ERROR opening " + filename);
            e.printStackTrace();
        }

        return new BufferedWriter(fstream);
    }

    private static void runSimulations() {
        int frequency;
        freqHelper = new FrequencyHelper(maxThreads);

        for (frequency = initialFreq; frequency <= endingFreq; frequency++) {
            freqHelper.setFrequency(frequency);
            System.out.println("# INFO freq = " + frequency + " reqs/min");
            System.out.println("# INFO period is " + freqHelper.getPeriod()
                    + " ms and thread period is " + freqHelper.getThreadPeriod() + " ms");
            runThreads();
        }
    }

    private static void runThreads() {
        final int totalThreads = freqHelper.getTotalThreads();
        System.out.println("# INFO Using " + totalThreads + " threads");

        final ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        freqHelper.setStartTime();

        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            final Runnable worker = new LoadGenerator(threadNumber);
            executor.execute(worker);
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(THREADS_TIMEOUT, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private Set<Purchase> purchase(final ShopList list) throws IOException {
        final long start = Calendar.getInstance().getTimeInMillis();
        final Set<Purchase> purchases = portal.purchase(list, getCustomerInfo());
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(purchaseLog, start, end);
        verifyPurchase(purchases);

        return purchases;
    }

    private void verifyPurchase(final Set<Purchase> purchases) {
        if (purchases.size() != 5) {
            System.err.println("# ERROR Purchase test! Length is not 5: " + purchases.size());
        }
    }

    private CustomerInfo getCustomerInfo() {
        final CustomerInfo info = new CustomerInfo();
        info.setAddress("Rua do Matao");
        info.setCreditCard("123 456 789");
        info.setName("Thread" + threadNumber);

        return info;
    }

    private static void logTime(final BufferedWriter out, final long start, final long end,
            final String... extraCols) {
        final StringBuffer line = new StringBuffer();
        line.append(end);
        line.append(' ');
        line.append(Long.toString((end - start)));

        for (String column : extraCols) {
            line.append(' ');
            line.append(column);
        }

        writeln(out, line);
    }

    private static void writeln(final BufferedWriter out, final StringBuffer line) {
        try {
            synchronized (LoadGenerator.class) {
                out.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("# ERROR writing to log");
            e.printStackTrace();
        }
    }

    private void requestDeliveries(final Set<Purchase> purchases) throws MalformedURLException {
        Delivery delivery;

        long start;
        long end;
        for (Purchase purchase : purchases) {
            start = Calendar.getInstance().getTimeInMillis();
            delivery = portal.getDelivery(purchase);
            end = Calendar.getInstance().getTimeInMillis();

            logTime(shipmentLog, start, end);
            verifyDelivery(delivery);
        }
    }

    private void verifyDelivery(final Delivery delivery) {
        if (delivery == null || !delivery.getStatus().equals("delivered")) {
            System.err.println("Shipment test failed!");
        }
    }

    private ShopList getLowestPriceList() throws IOException {
        final long start = Calendar.getInstance().getTimeInMillis();
        final ShopList list = portal.getLowestPrice(SHOPLIST);
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(lowestPriceLog, start, end);
        verifyLowestPriceList(list);

        return list;
    }

    private void verifyLowestPriceList(final ShopList list) {
        if (Math.abs(list.getPrice() - 1215) > 0.01) {
            System.err.println("# ERROR Price list test. Price is not 1215: " + list.getPrice());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < freqHelper.getTotalRequests(threadNumber); i++) {
            try {
                final long sleepTime = freqHelper.getSleepTime(threadNumber, i);
                if (isLate(sleepTime)) {
                    continue;
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("# ERROR on sleeping.");
                e.printStackTrace();
            }
            try {
                simulate();
            } catch (IOException e) {
                System.err.println("# ERROR on simulate()");
                e.printStackTrace();
            }
        }
    }

    private boolean isLate(final long sleepTime) {
        boolean late;

        if (sleepTime < 0) {
            synchronized (requests) {
                failures++;
                requests++;
                printFailures();
            }
            late = true;
        } else {
            late = false;
        }

        return late;
    }

    private void printFailures() {
        System.out.println("# INFO failures=" + failures + ", requests=" + requests + "("
                + (failures * 100) / requests + "%)");
    }

    private void simulate() throws IOException {
        final ShopList list = getLowestPriceList();
        final Set<Purchase> purchases = purchase(list);
        requestDeliveries(purchases);

        synchronized (requests) {
            requests++;
        }
    }
}