package br.usp.ime.futuremarket;

import java.util.Calendar;

/**
 * This class helps to calculate when to trigger events in order to match a
 * given frequency. The required time is one minute and there is support to
 * multiple instances (e.g.: load generator threads).
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
        setStartTime(getCurrentTime());
    }

    /**
     * @param Frequency
     *            per minute.
     * @throws InvalidArgumentException
     * @throws TooSmallPeriodException
     */
    public void setFrequency(final int frequencyPerMin) {
        totalThreads = Math.min(maxThreads, frequencyPerMin);
        period = 60000.0 / frequencyPerMin;
        threadPeriod = totalThreads * period;
    }

    public long getSleepTime(final int threadNumber, final int iteration) {
        final long eventTime = getEventTime(threadNumber, iteration);
        return Math.round(eventTime - getCurrentTime());
    }

    public long getEventTime(final int threadNumber, final int iteration) {
        return startTime + getEventDelay(threadNumber, iteration);
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private long getEventDelay(final int threadNumber, final int iteration) {
        final double firstThreadEvent = (threadNumber + 1) * period;
        final double iterationDelay = iteration * threadPeriod;
        return Math.round(firstThreadEvent + iterationDelay);
    }

    public int getTotalRequests(final int threadNumber) {
        final double firstEvent = ((threadNumber + 1) * period);
        return (int) ((60000 - firstEvent) / threadPeriod + 1);
    }

    // Trivial setters & getters

    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }

    public double getPeriod() {
        return period;
    }

    public double getThreadPeriod() {
        return threadPeriod;
    }

    public int getTotalThreads() {
        return totalThreads;
    }
}