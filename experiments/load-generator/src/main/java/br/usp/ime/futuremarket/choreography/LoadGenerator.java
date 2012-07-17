package br.usp.ime.futuremarket.choreography;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.usp.ime.futuremarket.choreography.models.LowestPrice;

public class LoadGenerator implements Runnable {
    private static Broker broker = null;
    private static Set<ProductQuantity> products = new HashSet<ProductQuantity>();
    private static final int THREADS_TIMEOUT = 360;

    private static final String LOWEST_PRICE_LOG = "lowest_price.log";
    private static final String PURCHASE_LOG = "purchase.log";
    private static final String SHIPMENT_LOG = "shipment.log";

    private static BufferedWriter lowestPriceLog;
    private static BufferedWriter purchaseLog;
    private static BufferedWriter shipmentLog;

    // Requests per minute
    private static int initialFreq;
    private static int endingFreq;
    private static int maxThreads;

    private static FrequencyHelper freqHelper;

    private String listId;
    private final int threadNumber;

    public static void main(final String[] args) {
        readArgs(args);

        broker = getBroker();
        populateProductList();

        openLogs();
        runSimulations();
        closeLogs();
    }

    public LoadGenerator(final int threadNumber) {
        this.threadNumber = threadNumber;
        System.out.println("Thread " + threadNumber + " has started.");
    }

    private static void readArgs(final String args[]) {
        initialFreq = Integer.parseInt(args[0]);
        endingFreq = Integer.parseInt(args[1]);
        maxThreads = Integer.parseInt(args[2]);
    }

    private static Broker getBroker() {
        final FutureMarket futureMarket = new FutureMarket();
        return futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Broker.class);
    }

    private static void populateProductList() {
        String product;
        int quantity;

        for (int i = 0; i < 10; i++) {
            product = "product" + (i + 1);
            quantity = i + 1;
            products.add(new ProductQuantity(product, quantity)); // NOPMD
        }
    }

    private static void openLogs() {
        lowestPriceLog = openLog(LOWEST_PRICE_LOG);
        purchaseLog = openLog(PURCHASE_LOG);
        shipmentLog = openLog(SHIPMENT_LOG);
    }

    private static BufferedWriter openLog(final String filename) {
        FileWriter fstream = null;

        try {
            fstream = new FileWriter(filename);
        } catch (IOException e) {
            System.err.println("Error opening " + filename);
            e.printStackTrace();
        }

        return new BufferedWriter(fstream);
    }

    private static void closeLogs() {
        try {
            lowestPriceLog.close();
            purchaseLog.close();
            shipmentLog.close();
        } catch (IOException e) {
            System.err.println("Error closing log file");
            e.printStackTrace();
        }
    }

    private static void runSimulations() {
        int frequency;
        freqHelper = new FrequencyHelper(maxThreads);

        for (frequency = initialFreq; frequency <= endingFreq; frequency++) {
            freqHelper.setFrequency(frequency);
            System.out.println("# " + frequency + " reqs/min");
            runThreads();
        }
    }

    private static void runThreads() {
        final int totalThreads = freqHelper.getTotalThreads();
        System.out.println("# Using " + totalThreads + " threads");

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

    private PurchaseInfo[] purchase(final LowestPrice list) {
        final long start = Calendar.getInstance().getTimeInMillis();

        final PurchaseInfo[] purchaseInfos = broker
                .makePurchase(list.getId(), new CustomerInfo());

        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(purchaseLog, start, end);

        verifyPurchase(purchaseInfos);

        return purchaseInfos;
    }

    private void verifyPurchase(final PurchaseInfo[] purchaseInfos) {
        if (purchaseInfos.length != 5) {
            System.err.println("Purchase test failed! Length is " + purchaseInfos.length);
        }
    }

    private static void logTime(final BufferedWriter out, final long start, final long end,
            final String... extraCols) {
        String line = end + " " + (end - start);

        for (String column : extraCols) {
            line += " " + column;
        }

        writeln(out, line);
    }

    private static void writeln(final BufferedWriter out, final String line) {
        try {
            synchronized (LoadGenerator.class) {
                out.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error while writing to file");
            e.printStackTrace();
        }
    }

    private void requestShipmentData(final PurchaseInfo[] purchaseInfos) {
        DeliveryInfo deliveryInfo;

        long start;
        long end;
        for (PurchaseInfo purchaseInfo : purchaseInfos) {
            start = Calendar.getInstance().getTimeInMillis();
            deliveryInfo = broker.getShipmentData(purchaseInfo);
            end = Calendar.getInstance().getTimeInMillis();

            logTime(shipmentLog, start, end, listId);

            verifyDelivery(deliveryInfo);
        }
    }

    private void verifyDelivery(final DeliveryInfo deliveryInfo) {
        if (deliveryInfo == null || !deliveryInfo.getStatus().equals("done")) {
            System.err.println("Shipment test failed!");
        }
    }

    private LowestPrice getLowestPriceList() {
        final long start = Calendar.getInstance().getTimeInMillis();
        final LowestPrice list = broker.getLowestPriceForList(products);
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(lowestPriceLog, start, end);

        verifyList(list);

        return list;
    }

    private void verifyList(final LowestPrice list) {
        if (!list.getPrice().equals(1215.0)) {
            System.err.println("Price list test failed! Price is " + list.getPrice());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < freqHelper.getTotalRequests(threadNumber); i++) {
            try {
                final long sleepTime = freqHelper.getSleepTime(threadNumber, i);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("# ERROR on sleeping.");
                e.printStackTrace();
            }
            simulate();
        }
    }

    private void simulate() {
        final LowestPrice list = getLowestPriceList();
        listId = list.getId();

        final PurchaseInfo[] purchaseInfos = purchase(list);

        requestShipmentData(purchaseInfos);
    }
}
