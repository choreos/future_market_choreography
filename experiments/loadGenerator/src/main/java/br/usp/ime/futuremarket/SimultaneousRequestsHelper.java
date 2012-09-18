package br.usp.ime.futuremarket;

import java.util.Calendar;

public class SimultaneousRequestsHelper {
    private static final int START_DELAY = 1000;

    private final int maxThreads;
    private int totalThreads;
    private double period;
    private long startTime;
    private int threadRequests;

    public SimultaneousRequestsHelper(final int maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * @param Frequency
     *            per minute.
     * @throws InvalidArgumentException
     * @throws TooSmallPeriodException
     */
    public void setFrequency(final int frequencyPerMin) {
        totalThreads = Math.min(maxThreads, frequencyPerMin);
        period = totalThreads * (60000.0 / frequencyPerMin);
        threadRequests = frequencyPerMin / totalThreads;
    }

    /**
     * This should always be one. TODO Throw exception if not one.
     * 
     * @return
     */
    public int getTotalThreadRequests() {
        return threadRequests;
    }

    public long getSleepTime(final int iteration) {
        final double eventTime = startTime + iteration * period;
        return Math.round(eventTime - getCurrentTime());
    }

    public void setStartTime() {
        setStartTime(getCurrentTime());
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public void setStartTime(final long startTime) {
        this.startTime = startTime + START_DELAY;
    }

    public double getPeriod() {
        return period;
    }

    public int getTotalThreads() {
        return totalThreads;
    }
}