package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

@SuppressWarnings("PMD.MoreThanOneLogger")
public final class FutureMarketLG {
    private static final int START_ORCH = 100;
    private static final int START_CHOR = 300;
    private static final int THREADS_TIMEOUT = 360;

    // Milliseconds
    private static final int TIMEOUT = 5000;

    private static String archType;
    private static int portals;
    // Requests per minute

    private static int start;
    private static final int STEP = 1;
    private static int max;

    private static final Logger GRAPH = Logger.getLogger("graphsLogger");
    private static final Logger CONSOLE = Logger.getLogger(FutureMarketClient.class);

    public static void main(final String[] args) throws IOException {
        archType = args[0];
        max = Integer.parseInt(args[1]);
        setUpClients();
        runSimulations();
    }

    private static AbstractPortalProxy getPortalProxy() throws IOException {
        AbstractPortalProxy proxies;

        if ("orch".equals(archType)) {
            proxies = new br.usp.ime.futuremarket.orchestration.PortalProxy();
            start = START_ORCH;
        } else {
            proxies = new br.usp.ime.futuremarket.choreography.PortalProxy();
            start = START_CHOR;
        }

        portals = proxies.size();

        return proxies;
    }

    private static void setUpClients() throws IOException {
        final AbstractPortalProxy portals = getPortalProxy();
        FutureMarketClient.setUp(TIMEOUT, portals);
    }

    private static void runSimulations() throws MalformedURLException {
        // warm up
        runSimulation(start);

        boolean timeout = false;
        int clients;
        for (clients = start; !timeout && clients <= max; clients += STEP) {
            timeout = runSimulation(clients);
        }

        final long timestamp = Calendar.getInstance().getTimeInMillis();
        final String result = String.format("%s,%d,%d,%d", archType, portals, clients - 2 * STEP,
                timestamp);
        GRAPH.info(result);
    }

    private static boolean runSimulation(final int clients) throws MalformedURLException {
        final String result = String.format("started %s,%d,%d", archType, portals, clients);
        CONSOLE.info(result);

        FutureMarketClient.resetStatistics();
        final CountDownLatch threadSync = new CountDownLatch(clients);
        FutureMarketClient.setCountDownLatch(threadSync);

        runThreads(clients);
        return hasTimedOut();
    }

    private static boolean hasTimedOut() {
        final int failures = FutureMarketClient.getFailures();
        final int requests = failures + FutureMarketClient.getSuccesses();

        final double percentage = ((double) failures * 100) / requests;
        CONSOLE.info(percentage);
        return (percentage >= 5.0);
    }

    private static void runThreads(final int totalThreads) throws MalformedURLException {
        final ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        Runnable worker;
        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            worker = new FutureMarketClient(threadNumber);
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
}