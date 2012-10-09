package br.usp.ime.futuremarket.choreography;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.FrequencyHelper;

public class FrequencyHelperTest {
    private static Calendar calendar;
    private long startTime;

    @BeforeClass
    public static void setCalendarInstance() {
        calendar = Calendar.getInstance();
    }

    private long getTime() {
        return calendar.getTimeInMillis();
    }

    /*-
     * 1000/min, 100 threads. Solution:
     * t00: 00000, 06000, 12000, 18000, ..., 54000
     * t01: 00060, 06060, 12060, 18060, ..., 54060 (10)
     * ...
     * t99: 05940, 11940, 17940, 23940, ..., 59940 (10)
     */
    @Test
    public void case1Test() {
        final FrequencyHelper freqHelper = new FrequencyHelper(100);
        freqHelper.setFrequency(1000);

        assertEquals(100, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 6000, freqHelper.getEventTime(0, 1));
        assertEquals(startTime + 54000, freqHelper.getEventTime(0, 9));

        assertEquals(startTime + 60, freqHelper.getEventTime(1, 0));
        assertEquals(startTime + 6060, freqHelper.getEventTime(1, 1));
        assertEquals(startTime + 54060, freqHelper.getEventTime(1, 9));

        assertEquals(startTime + 5940, freqHelper.getEventTime(99, 0));
        assertEquals(startTime + 11940, freqHelper.getEventTime(99, 1));
        assertEquals(startTime + 59940, freqHelper.getEventTime(99, 9));

        assertEquals(10, freqHelper.getTotalRequests(0));
        assertEquals(10, freqHelper.getTotalRequests(1));
        assertEquals(10, freqHelper.getTotalRequests(2));
    }

    /*-
     * 4/min, 1 thread. Solution:
     * t1: 0, 15k, 30k, 45k
     */
    @Test
    public void case2aTest() {
        final FrequencyHelper freqHelper = new FrequencyHelper(1);
        freqHelper.setFrequency(4);

        assertEquals(1, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 15000, freqHelper.getEventTime(0, 1));
        assertEquals(startTime + 30000, freqHelper.getEventTime(0, 2));
        assertEquals(startTime + 45000, freqHelper.getEventTime(0, 3));

        assertEquals(4, freqHelper.getTotalRequests(0));
    }

    /*-
     * 4/min, 2 threads. Solution:
     * t1: 0, 30k
     * t2: 15k, 45k
     */
    @Test
    public void case2bTest() {
        final FrequencyHelper freqHelper = new FrequencyHelper(2);
        freqHelper.setFrequency(4);

        assertEquals(2, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 30000, freqHelper.getEventTime(0, 1));

        assertEquals(startTime + 15000, freqHelper.getEventTime(1, 0));
        assertEquals(startTime + 45000, freqHelper.getEventTime(1, 1));

        assertEquals(2, freqHelper.getTotalRequests(0));
        assertEquals(2, freqHelper.getTotalRequests(1));
    }

    /*-
     * 4/min, 3 threads. Solution:
     * t1: 0, 45k
     * t2: 15k
     * t3: 30k
     */
    @Test
    public void case2cTest() {
        final FrequencyHelper freqHelper = new FrequencyHelper(3);
        freqHelper.setFrequency(4);

        assertEquals(3, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 45000, freqHelper.getEventTime(0, 1));

        assertEquals(startTime + 15000, freqHelper.getEventTime(1, 0));

        assertEquals(startTime + 30000, freqHelper.getEventTime(2, 0));

        assertEquals(2, freqHelper.getTotalRequests(0));
        assertEquals(1, freqHelper.getTotalRequests(1));
        assertEquals(1, freqHelper.getTotalRequests(2));
    }

    /*-
     * 4/min, 4 threads. Solution:
     * t1: 0
     * t2: 15k
     * t3: 30k
     * t4: 45k
     */
    @Test
    public void case2dTest() {
        final FrequencyHelper freqHelper = new FrequencyHelper(4);
        freqHelper.setFrequency(4);

        assertEquals(4, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 15000, freqHelper.getEventTime(1, 0));
        assertEquals(startTime + 30000, freqHelper.getEventTime(2, 0));
        assertEquals(startTime + 45000, freqHelper.getEventTime(3, 0));

        for (int i = 0; i < 4; i++) {
            assertEquals(1, freqHelper.getTotalRequests(i));
        }
    }

    /*-
     * 4/min, 5 threads. Solution:
     * t1: 0k
     * t2: 15k
     * t3: 30k
     * t4: 45k
     */
    @Test
    public void case2eTest() {
        final FrequencyHelper freqHelper = new FrequencyHelper(5);
        freqHelper.setFrequency(4);

        assertEquals(4, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime, freqHelper.getEventTime(0, 0));
        assertEquals(startTime + 15000, freqHelper.getEventTime(1, 0));
        assertEquals(startTime + 30000, freqHelper.getEventTime(2, 0));
        assertEquals(startTime + 45000, freqHelper.getEventTime(3, 0));

        for (int i = 0; i < 4; i++) {
            assertEquals(1, freqHelper.getTotalRequests(i));
        }
    }

    @Test
    public void shouldRoundNumbersCorrectly() {
        final FrequencyHelper freqHelper = new FrequencyHelper(800);
        freqHelper.setFrequency(14);

        assertEquals(14, freqHelper.getTotalThreads());

        startTime = getTime();
        freqHelper.setStartTime(startTime);

        assertEquals(startTime + 4286, freqHelper.getEventTime(1, 0));
        assertEquals(startTime + 8571, freqHelper.getEventTime(2, 0));
        assertEquals(startTime + 60000, freqHelper.getEventTime(14, 0));
    }
}
