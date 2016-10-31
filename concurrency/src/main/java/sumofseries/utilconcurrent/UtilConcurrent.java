package ua.yandex.sumofseries.utilconcurrent;

import java.util.*;
import java.util.concurrent.*;

public class UtilConcurrent implements Callable<Double> {

    private static final double step = 0.0001;
    private int maxNumber;
    private int maxNumberOfThread;
    private int tempThread;

    public UtilConcurrent(int maxNumber, int maxNumberOfThread,
            int tempThread) {
        this.maxNumber = maxNumber;
        this.maxNumberOfThread = maxNumberOfThread;
        this.tempThread = tempThread;
    }

    @Override
    public Double call() {
        double beginOfCycle;
        double result = 0;
        if (tempThread == maxNumberOfThread) {
            beginOfCycle = -maxNumber;
        } else {
            beginOfCycle = -maxNumber + step * tempThread;
        }
        for (double i = beginOfCycle; i <= 0; i += step * maxNumberOfThread) {
            result += Math.sin(i) * Math.cos(i) + Math.sin(-i) * Math.cos(-i);
        }
        return result;
    }

    static double sinSumExecutor(int thrNum, int sinCount) throws
            InterruptedException, ExecutionException {
        ExecutorService exService = Executors.newFixedThreadPool(thrNum);
        double result = 0.0;
        List<Future<Double>> results = new ArrayList<>();
        for (int i = 0; i < thrNum; i++) {
            results.add(i, exService.submit(new UtilConcurrent(i, thrNum,
                    sinCount)));
        }
        for (int i = 0; i < thrNum; i++) {
            while (!results.get(i).isDone()) {
            }
            result += results.get(i).get();
        }
        exService.shutdown();
        return result;
    }

}
