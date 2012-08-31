package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import br.usp.ime.futuremarket.choreography.Portal;
import br.usp.ime.futuremarket.choreography.PortalLoaderImplChor;
import br.usp.ime.futuremarket.orchestration.PortalLoaderImplOrch;

@SuppressWarnings("PMD.MoreThanOneLogger")
public final class FutureMarketLG {
    private static final int THREADS_TIMEOUT = 360;

    private static String archType;
    // Requests per minute
    private static int fistFreq;
    private static int freqStep;
    private static int lastFreq;
    private static int maxThreads;

    private static final Logger GRAPH = Logger.getLogger("graphsLogger");
    private static final Logger CONSOLE = Logger.getLogger(FutureMarketClient.class);
    private static FrequencyHelper freqHelper;

    private FutureMarketLG() {
    };

    public static void main(final String[] args) throws IOException {
        readArgs(args);
        freqHelper = new FrequencyHelper(maxThreads);
        setUpClients();
        runSimulations();
    }

    private static void readArgs(final String args[]) {
        archType = args[0];
        // Requests per minute
        fistFreq = Integer.parseInt(args[1]);
        freqStep = Integer.parseInt(args[2]);
        lastFreq = Integer.parseInt(args[3]);
        maxThreads = Integer.parseInt(args[4]);
    }

    private static void setUpClients() throws IOException {
        setPortals();
        FutureMarketClient.setFrequencyHelper(freqHelper);
    }

    private static void setPortals() throws IOException {
        PortalLoader loader;

        if ("orch".equals(archType)) {
            loader = new PortalLoaderImplOrch();
        } else {
            loader = new PortalLoaderImplChor();
        }

        final List<Portal> portals = loader.getPortals();
        FutureMarketClient.setPortals(portals);
    }

    private static void runSimulations() {
        int frequency;

        for (frequency = fistFreq; frequency <= lastFreq; frequency += freqStep) {
            final String title = String.format("# %s %s reqs/min", archType, frequency); 
            CONSOLE.info(title);
            GRAPH.info(title);
            
            freqHelper.setFrequency(frequency);
            GRAPH.info("# period=" + freqHelper.getPeriod() + " ms, thread period="
                    + freqHelper.getThreadPeriod() + " ms");
            runThreads();
        }
    }

    private static void runThreads() {
        final int totalThreads = freqHelper.getTotalThreads();
        CONSOLE.debug("Using " + totalThreads + " threads");

        final ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        freqHelper.setStartTime();

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