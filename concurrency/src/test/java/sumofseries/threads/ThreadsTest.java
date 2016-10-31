package ua.yandex.sumofseries.threads;

import static org.junit.Assert.*;
import org.junit.Test;

public class ThreadsTest {

    @Test
    public void testOneThread() {
        int maxNumber = 10;
        double step = 0.0001;
        double expResult = 0.0;
        for (double i = -maxNumber; i <= 0; i += step) {
            expResult = expResult + Math.sin(i) * Math.cos(i)
                    + Math.sin(-i) * Math.cos(i);
        }
        double actResult = 0.0;
        assertEquals(expResult, actResult, 0.0001);
    }

    @Test
    public void testForTwoThreadCheckResult() throws InterruptedException {
        double expRes = Threads.sinSum(2, 10);
        double actRes = 0.0;
        assertEquals(expRes, actRes, 0.00001);
    }

    @Test
    public void testForThreeThreadCheckResult() throws InterruptedException {
        double expRes = Threads.sinSum(3, 10);
        double actRes = actRes = 0.0;
        assertEquals(expRes, actRes, 0.00001);
    }
}
