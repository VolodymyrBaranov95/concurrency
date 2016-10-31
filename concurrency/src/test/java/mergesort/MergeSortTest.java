package ua.yandex.mergesort;

import static org.junit.Assert.*;
import org.junit.Test;

public class MergeSortTest {

    @Test
    public void mergeSortCheckOrder() {
        int arrCount = 6;
        int mas[] = {9, 8, 7, 6, 5, 4};
        int res[] = new int[arrCount + 1];
        MergeSortThread toSort = new MergeSortThread(mas, 1, arrCount, res, 1);
        Thread thr = new Thread(toSort);
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
        }
        boolean f = true;
        for (int i = 1; i < arrCount; i++) {
            if (res[i] > res[i + 1]) {
                f = false;
            }
        }
        assertTrue(f);
    }

    @Test
    public void mergeSortTestEqualsArray() {
        int arrCount = 5;
        int mas[] = {0, 30, 20, 10, 5,40};
        int res[] = new int[arrCount+1];
        MergeSortThread toSort = new MergeSortThread(mas, 1, arrCount, res, 1);
        Thread thr = new Thread(toSort);
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
        }
        int actArray[] = {0,5, 10, 20,30,40};
        assertArrayEquals(actArray, res);
    }
}
