package br.usp.ime.futuremarket;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloWorldLG implements Runnable {
    private static HelloWorld helloWorld;
    private static final int THREADS_TIMEOUT = 360;
    private static final Random RANDOM = new Random();

    // Requests per minute
    private static int initialFreq;
    private static int endingFreq;
    private static int maxThreads;

    // Milliseconds
    private static int gap;
    private static double period;
    private int threadNumber;

    public static void main(String args[]) throws IOException {
        readArgs(args);
        helloWorld = HelloWorldImpl.getWSClient();
        simulate();
    }

    private static void readArgs(final String args[]) {
        initialFreq = Integer.parseInt(args[0]);
        endingFreq = Integer.parseInt(args[1]);
        maxThreads = Integer.parseInt(args[2]);
    }

    public static void simulate() {
        int frequency;

        for (frequency = initialFreq; frequency <= endingFreq; frequency++) {
            simulate(frequency);
        }
    }

    private static void simulate(final int frequency) {
        final int totalThreads = Math.min(maxThreads, frequency);
        period = 60 * 1000.0 / frequency;
        gap = (int) (totalThreads * period + 0.5);

        HelloWorldLG.println("# " + frequency + " reqs/min, gap " + gap + "ms, period " + period
                + "ms");

        runThreads(totalThreads);
    }

    private static void runThreads(final int totalThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int threadNumber = 0; threadNumber < totalThreads; threadNumber++) {
            Runnable worker = new HelloWorldLG(threadNumber);
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

    public HelloWorldLG(final int threadNumber) {
        this.threadNumber = threadNumber;
    }

    private void checkAnswer(final String answer, final String name) {
        final String expected = "Hello, " + name + "!";

        if (!answer.equals(expected)) {
            HelloWorldLG.println(System.err, "ERROR: received '" + answer + "', expected '"
                    + expected + "'.");
        }
    }

    @Override
    public void run() {
        final long start = Calendar.getInstance().getTimeInMillis();
        final long end = start + 60 * 1000;
        long nextRequest = start + (long) ((threadNumber + 1) * period + 0.5);
        String name, answer, log;

        while (nextRequest <= end) {
            try {
                Thread.sleep(nextRequest - Calendar.getInstance().getTimeInMillis());
            } catch (InterruptedException e) {
                HelloWorldLG.println(System.err, "ERROR on Thread.sleep");
                e.printStackTrace();
            }

            name = String.valueOf(RANDOM.nextInt(60 * 1000 * 5));
            log = threadNumber + " " + Calendar.getInstance().getTimeInMillis();
            answer = helloWorld.sayHello(name);
            HelloWorldLG.println(log + " " + Calendar.getInstance().getTimeInMillis());

            checkAnswer(answer, name);

            nextRequest += gap;
        }
    }

    private synchronized static void println(final String line) {
        HelloWorldLG.println(System.out, line);
    }

    private synchronized static void println(final PrintStream output, final String line) {
        output.println(line);
    }
}
