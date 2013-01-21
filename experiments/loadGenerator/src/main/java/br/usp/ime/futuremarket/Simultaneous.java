package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Simultaneous extends AbstractLoadGenerator {

    private int minClients, maxClients, maxTime;

    @Override
    public void generateLoad(final String[] args, final int start) throws IOException {
        readArgs(args, start);
        setUpClients();
        runSimulations();
    }

    public static String getHelpMessage() {
        return "{chor,orch} minClients maxClients step maxTimeMili)";
    }

    private void readArgs(final String[] args, final int start) {
        archType = args[start];
        minClients = Integer.parseInt(args[start + 1]);
        maxClients = Integer.parseInt(args[start + 2]);
        step = Integer.parseInt(args[start + 3]);
        maxTime = Integer.parseInt(args[start + 4]);
    }

    private void setUpClients() throws IOException {
        final AbstractPortalProxy portals = getPortalProxies();
        SimultaneousClient.setPortals(portals);
    }

    private void runSimulations() throws MalformedURLException {
        warmUp(minClients);

        long duration = 0;
        for (int clients = minClients; duration <= maxTime && clients <= maxClients; clients += step) {
            logTitle(clients);
            duration = runSimulation(clients);
        }
    }

    private void logTitle(final int clients) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final String header = String.format("%s,%d,%d,%d", archType, portals, clients, now);

        CONSOLE.info(header);
        GRAPH.info("# " + header);
    }

    protected long runSimulation(final int clients) throws MalformedURLException {
        final CountDownLatch threadSync = new CountDownLatch(clients);
        SimultaneousClient.setCountDownLatch(threadSync);

        final long start = Calendar.getInstance().getTimeInMillis();
        runThreads(clients);
        final long end = Calendar.getInstance().getTimeInMillis();

        return (end - start);
    }

    protected void warmUp(final int threads) throws MalformedURLException {
        CONSOLE.info("warmup");
        GRAPH.info("# warmup");

        runSimulation(threads);
        runSimulation(threads);
    }

    @SuppressWarnings("PMD.WhileLoopsMustUseBraces")
    protected void runThreads(final int totalThreads) throws MalformedURLException {
        final ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        Runnable worker;
        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            worker = new SimultaneousClient(threadNumber); // NOPMD
            executor.execute(worker);
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(THREADS_TIMEOUT, TimeUnit.SECONDS))
                ; // NOPMD
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}