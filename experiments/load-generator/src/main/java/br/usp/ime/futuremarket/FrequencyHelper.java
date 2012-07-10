package br.usp.ime.futuremarket;

import java.util.Calendar;

/**
 * This class helps to calculate when to trigger events in order to match a given frequency. The
 * required time is one minute and there is support to multiple instances (e.g.: load generator
 * threads).
 * 
 * @author Cadu
 * 
 */
public class FrequencyHelper {
    // period, threadPeriod are in milliseconds.
    private double period, threadPeriod;
    private int totalThreads;
    private final int maxThreads;
    private long startTime;

    public FrequencyHelper(final int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void setStartTime() {
        startTime = getCurrentTime();
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public long getSleepTime(final int threadNumber, final int iteration) {
        final long nextEventTime = startTime + getNextEventTime(threadNumber, iteration);
        return Math.round(nextEventTime - getCurrentTime());
    }

    private long getNextEventTime(final int threadNumber, final int iteration) {
        final long firstEvent = (long) ((threadNumber + 1) * period);
        final long iterationDelay = (long) (iteration * threadPeriod);
        return (long) (firstEvent + iterationDelay);
    }

    /**
     * @param Frequency
     *            per minute. A divisor of 60,000
     * @throws InvalidArgumentException
     * @throws TooSmallPeriodException
     */
    public void setFrequency(final int frequencyPerMin) throws InvalidArgumentException {
        totalThreads = Math.min(maxThreads, frequencyPerMin);
        period = 60000.0 / frequencyPerMin;

        threadPeriod = totalThreads * period;
    }

    public int getTotalThreads() {
        return totalThreads;
    }
}

class InvalidArgumentException extends Exception {

    public InvalidArgumentException(final String message) {
        super(message);
    }
}