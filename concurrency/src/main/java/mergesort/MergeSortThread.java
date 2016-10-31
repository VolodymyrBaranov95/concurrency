package ua.yandex.mergesort;

public class MergeSortThread implements Runnable {

    int curArr[];
    int resArr[];
    int p;
    int r;
    int s;

    public MergeSortThread(int curArr[], int p, int r, int resArr[], int s) {
        this.curArr = curArr;
        this.p = p;
        this.r = r;
        this.resArr = resArr;
        this.s = s;
    }

    @Override
    public void run() {
        int n = r - p + 1;
        if (n == 1) {
            resArr[s] = curArr[p];
        } else {
            int tempArr[] = new int[n + 1];
            int q = (p + r) / 2;
            int q1 = q - p + 1;
            MergeSortThread first = new MergeSortThread(curArr, p,
                    q, tempArr, 1);
            Thread thr1 = new Thread(first);
            thr1.start();
            MergeSortThread second = new MergeSortThread(curArr, q + 1,
                    r, tempArr, q1 + 1);
            second.run();
            try {
                thr1.join();
            } catch (InterruptedException e) {
            }
            MergeThread toMerge = new MergeThread(tempArr, 1, q1,
                    q1 + 1, n, resArr, s);
            toMerge.run();
        }
    }
}
