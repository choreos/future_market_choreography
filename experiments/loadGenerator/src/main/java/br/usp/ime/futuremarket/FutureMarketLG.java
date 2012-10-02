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
    private static final int START_ORCH = 1;
    private static final int START_CHOR = 1;
    private static final int THREADS_TIMEOUT = 360;
    private static final int MAX_TIME = 120000;

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

    private static AbstractPortalProxy getPortalProxies() throws IOException {
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
        final AbstractPortalProxy portals = getPortalProxies();
        FutureMarketClient.setPortals(portals);
    }

    private static void runSimulations() throws MalformedURLException {
        warmUp();

        long startTime, finishTime;
        for (int clients = start; clients <= max; clients += STEP) {
            printHeader(clients);

            startTime = Calendar.getInstance().getTimeInMillis();
            runSimulation(clients);
            finishTime = Calendar.getInstance().getTimeInMillis();

            if (finishTime - startTime > MAX_TIME) {
                break;
            }
        }
    }

    private static void warmUp() throws MalformedURLException {
        CONSOLE.info("warmup");
        GRAPH.info("# warmup");

        runSimulation(1);
        runSimulation(1);
    }

    private static void printHeader(final int clients) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final String header = String.format("%s,%d,%d,%d", archType, portals, clients, now);

        CONSOLE.info(header);
        GRAPH.info("# " + header);
    }

    private static void runSimulation(final int clients) throws MalformedURLException {
        final CountDownLatch threadSync = new CountDownLatch(clients);
        FutureMarketClient.setCountDownLatch(threadSync);

        runThreads(clients);
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