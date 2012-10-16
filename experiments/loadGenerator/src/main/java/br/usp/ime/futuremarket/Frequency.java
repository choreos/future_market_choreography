package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Frequency extends AbstractLoadGenerator {

    private FrequencyHelper freqHelper;

    // Requests per minute
    private int minFreq, maxFreq;
    // milliseconds
    private int successTime;
    private int maxThreads;
    private float successRatio;

    @Override
    public void generateLoad(final String[] args, final int start) throws IOException {
        readArgs(args, start);
        freqHelper = new FrequencyHelper(maxThreads);
        setUpClients();
        runSimulations();
    }

    public static String getHelpMessage() {
        return "{chor,orch} minFreq maxFreq step successTime successRatio maxThreads";
    }

    private void readArgs(final String[] args, final int start) {
        archType = args[start];
        minFreq = Integer.parseInt(args[start + 1]);
        maxFreq = Integer.parseInt(args[start + 2]);
        step = Integer.parseInt(args[start + 3]);
        successTime = Integer.parseInt(args[start + 4]);
        successRatio = Float.parseFloat(args[start + 5]);
        maxThreads = Integer.parseInt(args[start + 6]);
    }

    private void setUpClients() throws IOException {
        final AbstractPortalProxy portals = getPortalProxies();
        FrequencyClient.setup(portals, freqHelper, successTime);
    }

    private void runSimulations() throws MalformedURLException {
        warmUp(minFreq);

        String result;
        for (int frequency = minFreq; frequency <= maxFreq; frequency += step) {
            logTitle(frequency);

            freqHelper.setFrequency(frequency);
            logFrequency();

            runSimulation(freqHelper.getTotalThreads());

            if (hasTimedOut()) {
                result = String.format("result: %s,%d,%d ", archType, portals, frequency - step);
                CONSOLE.info(result);
                GRAPH.info("# " + result);
                break;
            }
        }
    }

    protected void warmUp(final int freq) throws MalformedURLException {
        CONSOLE.info("warmup");
        GRAPH.info("# warmup");

        freqHelper.setFrequency(freq);
        final int threads = freqHelper.getTotalThreads();

        runSimulation(threads);
    }

    protected void runSimulation(final int threads) throws MalformedURLException {
        resetClients();
        runThreads(threads);
    }

    private void logTitle(final int frequency) {
        final String title = String.format("# %s,%d,%d", archType, portals, frequency);
        CONSOLE.info(title);
        GRAPH.info(title);
    }

    private void logFrequency() {
        final String msg = String.format("# period=%s, threadPeriod=%s", freqHelper.getPeriod(),
                freqHelper.getThreadPeriod());
        CONSOLE.info(msg);
        GRAPH.info(msg);
    }

    private void resetClients() {
        FrequencyClient.resetStatistics();
        final CountDownLatch threadSync = new CountDownLatch(freqHelper.getTotalThreads());
        FrequencyClient.setCountDownLatch(threadSync);
    }

    private boolean hasTimedOut() {
        final int failures = FrequencyClient.getFailures();
        final int successes = FrequencyClient.getSuccesses();
        final float successRatio = ((float) successes) / (successes + failures);

        final String message = String.format("# successes: %s/%s (%.2f%%)", successes,
                (failures + successes), successRatio * 100);
        CONSOLE.info(message);
        GRAPH.info(message);

        return (successRatio < this.successRatio);
    }

    @SuppressWarnings("PMD.WhileLoopsMustUseBraces")
    private void runThreads(final int totalThreads) throws MalformedURLException {
        CONSOLE.debug("Using " + totalThreads + " threads");
        final ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        Runnable worker;
        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            worker = new FrequencyClient(threadNumber); // NOPMD
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