package br.usp.ime.futuremarket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.usp.ime.futuremarket.models.LowestPrice;

public class LoadGenerator implements Runnable {
    private Orchestrator orchestrator = null;
    private static Set<ProductQuantity> products = new HashSet<ProductQuantity>();
    private static final int THREADS_TIMEOUT = 360;

    private static final String LOWEST_PRICE_LOG = "lowest_price.log";
    private static final String PURCHASE_LOG = "purchase.log";
    private static final String SHIPMENT_LOG = "shipment.log";

    private static BufferedWriter lowestPrice;
    private static BufferedWriter purchase;
    private static BufferedWriter shipment;

    private static int threadSimulations;

    private String listId;

    public LoadGenerator(final int threadNumber) {
        orchestrator = getOrchestrator();
        System.out.println("Thread " + threadNumber + " has started.");
    }

    private static void openLogs() {
        lowestPrice = openLog(LOWEST_PRICE_LOG);
        purchase = openLog(PURCHASE_LOG);
        shipment = openLog(SHIPMENT_LOG);
    }

    private static BufferedWriter openLog(final String filename) {
        FileWriter fstream = null;

        try {
            fstream = new FileWriter(filename);
        } catch (IOException e) {
            System.err.println("Error while opening " + filename);
            e.printStackTrace();
        }

        return new BufferedWriter(fstream);
    }

    public static void main(String[] args) {
        populateProductList();

        final int totalThreads = Integer.parseInt(args[0]);
        threadSimulations = Integer.parseInt(args[1]);

        System.out.println(totalThreads + " threads will be created for " + threadSimulations
                + " simulations each:");

        openLogs();
        runThreads(totalThreads);
        closeLogs();
    }

    private static void populateProductList() {
        String product;
        int quantity;

        for (int i = 0; i < 10; i++) {
            product = "product" + (i + 1);
            quantity = i + 1;
            products.add(new ProductQuantity(product, quantity));
        }
    }

    private static void runThreads(final int totalThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            Runnable worker = new LoadGenerator(threadNumber);
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

    private static void closeLogs() {
        try {
            lowestPrice.close();
            purchase.close();
            shipment.close();
        } catch (IOException e) {
            System.err.println("Error while closing log file");
            e.printStackTrace();
        }
    }

    private static Orchestrator getOrchestrator() {
        final FutureMarket futureMarket = new FutureMarket();
        return futureMarket.getFirstClient(FutureMarket.ORCHESTRATOR_ROLE,
                FutureMarket.ORCHESTRATOR_SERVICE, Orchestrator.class);
    }

    private void simulate() {
        final LowestPrice list = getLowestPriceList();
        listId = list.getId();

        final PurchaseInfo[] purchaseInfos = purchase(list);

        requestShipmentData(purchaseInfos);
    }

    private PurchaseInfo[] purchase(final LowestPrice list) {
        final long start = Calendar.getInstance().getTimeInMillis();

        final PurchaseInfo[] purchaseInfos = orchestrator.makePurchase(list.getId(),
                new CustomerInfo());

        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(purchase, start, end);

        verifyPurchase(purchaseInfos);

        return purchaseInfos;
    }

    private void verifyPurchase(PurchaseInfo[] purchaseInfos) {
        if (purchaseInfos.length != 5) {
            System.err.println("Purchase test failed! Length is " + purchaseInfos.length);
        }
    }

    private static void logTime(final BufferedWriter out, final long start, final long end,
            String... extraCols) {
        String line = end + " " + (end - start);

        for (String column : extraCols) {
            line = line + " " + column;
        }

        writeln(out, line);
    }

    private static void writeln(final BufferedWriter out, String line) {
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
            deliveryInfo = orchestrator.getShipmentData(purchaseInfo);
            end = Calendar.getInstance().getTimeInMillis();

            logTime(shipment, start, end, listId);

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
        final LowestPrice list = orchestrator.getLowestPriceForList(products);
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(lowestPrice, start, end);

        verifyList(list);

        return list;
    }

    private void verifyList(LowestPrice list) {
        if (!list.getPrice().equals(1215.0)) {
            System.err.println("Price list test failed! Price is " + list.getPrice());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < threadSimulations; i++) {
            simulate();
        }
    }
}
