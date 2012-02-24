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
    private static final String LOG_FILE = "log.txt";
    private static BufferedWriter out;

    public LoadGenerator(final int threadNumber) {
        System.out.println("Thread " + threadNumber + " started.");
    }

    private static BufferedWriter openLog() {
        FileWriter fstream = null;

        try {
            fstream = new FileWriter(LOG_FILE);
        } catch (IOException e) {
            System.err.println("Error while opening " + LOG_FILE);
            e.printStackTrace();
        }

        return new BufferedWriter(fstream);
    }

    public static void main(String[] args) {
        customer = getCustomer();
        out = openLog();

        final int totalThreads = Integer.parseInt(args[0]);
        threadDuration = Integer.parseInt(args[1]);

        System.out.println(totalThreads + " threads will be created for " + threadDuration
                + " seconds:");

        runThreads(totalThreads, threadDuration);

        closeLog();
    }

    private static void runThreads(final int totalThreads, final int threadDuration) {
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            Runnable worker = new LoadGenerator(i);
            executor.execute(worker);
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(threadDuration + 30, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private static void closeLog() {
        try {
            out.close();
        } catch (IOException e) {
            System.err.println("Error while closing file");
            e.printStackTrace();
        }
    }

    private static Customer getCustomer() {
        final FutureMarket futureMarket = new FutureMarket();
        return futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);
    }

    private long simulate() {
        final long start = Calendar.getInstance().getTimeInMillis();

        final LowestPrice list = getLowestPriceList();

        final PurchaseInfo[] purchaseInfos = customer
                .makePurchase(list.getId(), new CustomerInfo());

        requestShipmentData(purchaseInfos);

        return Calendar.getInstance().getTimeInMillis() - start;
    }

    private static synchronized void logTime(final String time, final String duration) {
        try {
            out.write(time + " " + duration + "\n");
        } catch (IOException e) {
            System.err.println("Error while writing to file");
            e.printStackTrace();
        }
    }

    private void requestShipmentData(final PurchaseInfo[] purchaseInfos) {
        for (PurchaseInfo purchaseInfo : purchaseInfos) {
            customer.getShipmentData(purchaseInfo);
        }
    }

    private LowestPrice getLowestPriceList() {
        final String[] products = { "product1", "product2", "product3" };
        final LowestPrice list = customer.getLowestPriceForList(products);
        return list;
    }

    @Override
    public void run() {
        final long start = Calendar.getInstance().getTimeInMillis();

        long duration;
        long now;
        while (Calendar.getInstance().getTimeInMillis() - start < threadDuration * 1000) {
            now = Calendar.getInstance().getTimeInMillis();
            duration = simulate();
            logTime(Long.toString(now), Long.toString(duration));
        }
    }
}