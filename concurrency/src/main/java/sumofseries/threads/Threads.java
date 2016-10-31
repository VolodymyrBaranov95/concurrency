package ua.yandex.sumofseries.threads;
// Сразу заметим , что sin(x)*cos(x)+sin(-x)*cos(-x) = cos(x)(sin(x)+sin(-x))=0;
//Тогда вся искомая сумма = 0 ; 
//Тогда будем кажое слагаемое будет вида sin(x)*cos(x)+sin(-x)*cos(-x) - чтобы 
//ответ был похож на 0 (а то при суммировании в double - ответы очень разные);
//Самая быстрая работа буде при двух потоках - по количеству ядер . 
//С интерфейсом Callable - все немного удобнее.

public class Threads implements Runnable {

    private static final double step = 0.0001;
    private int maxNumber;
    private int maxNumberOfThread;
    private double result;
    private int tempThread;

    public Threads(int maxNumber, int maxNumberOfThread, int tempThread) {
        this.maxNumber = maxNumber;
        this.maxNumberOfThread = maxNumberOfThread;
        this.tempThread = tempThread;
        result = 0;
    }

    @Override
    public void run() {
        double beginOfCycle;
        if (tempThread == maxNumberOfThread) {
            beginOfCycle = -maxNumber;
        } else {
            beginOfCycle = -maxNumber + step * tempThread;
        }
        for (double i = beginOfCycle; i <= 0; i += step * maxNumberOfThread) {
            result += Math.sin(i) * Math.cos(i) + Math.sin(-i) * Math.cos(-i);
        }
    }

    static double sinSum(int thrNum, int sinCount) {
        Threads[] threads = new Threads[thrNum];
        Thread[] thr = new Thread[thrNum];
        for (int i = 0; i < thrNum; i++) {
            threads[i] = new Threads(sinCount, thrNum, i);
            thr[i] = new Thread(threads[i]);
            thr[i].start();
        }
        double result = 0;
        for (int i = 0; i < thrNum; i++) {
            try {
                thr[i].join();
            } catch (InterruptedException e) {
            };
            result += threads[i].getResult();
        }
        return result;
    }

    public double getResult() {
        return result;
    }
}
