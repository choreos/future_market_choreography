package br.usp.ime.futuremarket;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FrequencyHelperTest {

    /*-
     * 1000/min, 100 threads. Solution:
     * t00: 00060, 06060, 12060, 18060, ..., 54060 (10)
     * t01: 00120, 06120, 12120, 18120, ..., 54120 (10)
     * ...
     * t99: 06000, 12000, 18000, 24000, ..., 60000 (10)
     */
    @Test
    public void case1Test() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(100);
        freqHelper.setFrequency(1000);

        assertEquals(100, freqHelper.getTotalThreads());

        freqHelper.setStartTime();
        assertEquals(60, freqHelper.getSleepTime(0, 0));
        assertEquals(6060, freqHelper.getSleepTime(0, 1));
        assertEquals(54060, freqHelper.getSleepTime(0, 9));
        
        assertEquals(120, freqHelper.getSleepTime(1, 0));
        assertEquals(6120, freqHelper.getSleepTime(1, 1));
        assertEquals(54120, freqHelper.getSleepTime(1, 9));
        
        assertEquals(6000, freqHelper.getSleepTime(99, 0));
        assertEquals(12000, freqHelper.getSleepTime(99, 1));
        assertEquals(60000, freqHelper.getSleepTime(99, 9));
        
    }

    /*-
     * 4/min, 1 thread. Solution:
     * t1: 15k, 30k, 45k, 60k
     */
    @Test
    public void case2aTest() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(1);
        freqHelper.setFrequency(4);

        assertEquals(1, freqHelper.getTotalThreads());
        
        freqHelper.setStartTime();
        assertEquals(15000, freqHelper.getSleepTime(0, 0));
        assertEquals(30000, freqHelper.getSleepTime(0, 1));
        assertEquals(45000, freqHelper.getSleepTime(0, 2));
        assertEquals(60000, freqHelper.getSleepTime(0, 3));
    }

    /*-
     * 4/min, 2 threads. Solution:
     * t1: 15k, 45k
     * t2: 30k, 60k
     */
    @Test
    public void case2bTest() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(2);
        freqHelper.setFrequency(4);

        assertEquals(2, freqHelper.getTotalThreads());

        freqHelper.setStartTime();
        assertEquals(15000, freqHelper.getSleepTime(0, 0));
        assertEquals(45000, freqHelper.getSleepTime(0, 1));
        
        assertEquals(30000, freqHelper.getSleepTime(1, 0));
        assertEquals(60000, freqHelper.getSleepTime(1, 1));
    }

    /*-
     * 4/min, 3 threads. Solution:
     * t1: 15k, 60k
     * t2: 30k
     * t3: 45k
     */
    @Test
    public void case2cTest() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(3);
        freqHelper.setFrequency(4);

        assertEquals(3, freqHelper.getTotalThreads());

        freqHelper.setStartTime();
        assertEquals(15000, freqHelper.getSleepTime(0, 0));
        assertEquals(60000, freqHelper.getSleepTime(0, 1));
        
        assertEquals(30000, freqHelper.getSleepTime(1, 0));
        
        assertEquals(45000, freqHelper.getSleepTime(2, 0));
    }

    /*-
     * 4/min, 4 threads. Solution:
     * t1: 15k
     * t2: 30k
     * t3: 45k
     * t4: 60k
     */
    @Test
    public void case2dTest() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(4);
        freqHelper.setFrequency(4);

        assertEquals(4, freqHelper.getTotalThreads());
        
        freqHelper.setStartTime();
        assertEquals(15000, freqHelper.getSleepTime(0, 0));
        assertEquals(30000, freqHelper.getSleepTime(1, 0));
        assertEquals(45000, freqHelper.getSleepTime(2, 0));
        assertEquals(60000, freqHelper.getSleepTime(3, 0));
    }

    /*-
     * 4/min, 5 threads. Solution:
     * t1: 15k
     * t2: 30k
     * t3: 45k
     * t4: 60k
     */
    @Test
    public void case2eTest() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(5);
        freqHelper.setFrequency(4);

        assertEquals(4, freqHelper.getTotalThreads());
        
        freqHelper.setStartTime();
        assertEquals(15000, freqHelper.getSleepTime(0, 0));
        assertEquals(30000, freqHelper.getSleepTime(1, 0));
        assertEquals(45000, freqHelper.getSleepTime(2, 0));
        assertEquals(60000, freqHelper.getSleepTime(3, 0));
    }

    /*-
     * 120k/min, 1 thread. Solution:
     * t1: 0.5, 1.0, 1.5, ..., 60k (not a valid solution. threadPeriod < 1ms)
     */
    @Test(expected = InvalidArgumentException.class)
    public void shouldThrowExceptionIfThreadPeriodIsLessThan1Ms() throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(1);
        freqHelper.setFrequency(120000);
    }
    
    @Test
    public void shouldRoundNumbersCorrectly()
            throws InvalidArgumentException {
        final FrequencyHelper freqHelper = new FrequencyHelper(800);
        freqHelper.setFrequency(14);
        
        assertEquals(14, freqHelper.getTotalThreads());
        
        freqHelper.setStartTime();
        assertEquals(4286, freqHelper.getSleepTime(0, 0));
        assertEquals(8571, freqHelper.getSleepTime(1, 0));
        assertEquals(60000, freqHelper.getSleepTime(13, 0));
    }
}
