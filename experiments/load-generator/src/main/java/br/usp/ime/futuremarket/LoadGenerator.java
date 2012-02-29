package br.usp.ime.futuremarket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.usp.ime.futuremarket.models.LowestPrice;

public class LoadGenerator implements Runnable {
    private static Customer customer = null;
    private static int threadDuration;

    private static final String LOWEST_PRICE_LOG = "lowest_price.log";
    private static final String PURCHASE_LOG = "purchase.log";
    private static final String SHIPMENT_LOG = "shipment.log";

    private static BufferedWriter lowestPrice;
    private static BufferedWriter purchase;
    private static BufferedWriter shipment;

    public LoadGenerator(final int threadNumber) {
        System.out.println("Thread " + threadNumber + " started.");
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
        customer = getCustomer();
        openLogs();

        final int totalThreads = Integer.parseInt(args[0]);
        threadDuration = Integer.parseInt(args[1]);

        System.out.println(totalThreads + " threads will be created for " + threadDuration
                + " seconds:");

        runThreads(totalThreads, threadDuration);

        closeLogs();
    }

    private static void runThreads(final int totalThreads, final int threadDuration) {
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            Runnable worker = new LoadGenerator(i);
            executor.execute(worker);
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(threadDuration + 60, TimeUnit.SECONDS))
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

    private static Customer getCustomer() {
        final FutureMarket futureMarket = new FutureMarket();
        return futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);
    }

    private void simulate() {
        final LowestPrice list = getLowestPriceList();

        final PurchaseInfo[] purchaseInfos = purchase(list);

        requestShipmentData(purchaseInfos);
    }

    private PurchaseInfo[] purchase(final LowestPrice list) {
        final long start = Calendar.getInstance().getTimeInMillis();

        final PurchaseInfo[] purchaseInfos = customer
                .makePurchase(list.getId(), new CustomerInfo());
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(purchase, start, end);

        verifyPurchase(purchaseInfos);

        return purchaseInfos;
    }

    private void verifyPurchase(PurchaseInfo[] purchaseInfos) {
        if (purchaseInfos.length != 3) {
            System.err.println("Purchase test failed!");
        }
    }

    private static synchronized void logTime(final BufferedWriter out, final long start,
            final long end) {
        try {
            out.write(start + " " + (end - start) + "\n");
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
            deliveryInfo = customer.getShipmentData(purchaseInfo);
            end = Calendar.getInstance().getTimeInMillis();

            logTime(shipment, start, end);

            verifyDelivery(deliveryInfo);
        }
    }

    private void verifyDelivery(final DeliveryInfo deliveryInfo) {
        if (deliveryInfo == null || !deliveryInfo.getStatus().equals("done")) {
            System.err.println("Shipment test failed!");
        }
    }

    private LowestPrice getLowestPriceList() {
        final String[] products = { "product1", "product2", "product3" };

        final long start = Calendar.getInstance().getTimeInMillis();
        final LowestPrice list = customer.getLowestPriceForList(products);
        final long end = Calendar.getInstance().getTimeInMillis();

        logTime(lowestPrice, start, end);

        verifyList(list);

        return list;
    }

    private void verifyList(LowestPrice list) {
        if (!list.getPrice().equals(6d)) {
            System.err.println("Price list test failed!");
        }
    }

    @Override
    public void run() {
        final long start = Calendar.getInstance().getTimeInMillis();

        while (Calendar.getInstance().getTimeInMillis() - start < threadDuration * 1000) {
            simulate();
        }
    }
}