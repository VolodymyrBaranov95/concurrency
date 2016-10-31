package ua.yandex.mergesort;

public class MergeThread implements Runnable {

    int curArr[];
    int p1;
    int r1;
    int p2;
    int r2;
    int resArr[];
    int p3;

    public MergeThread(int curArr[], int p1, int r1,
            int p2, int r2, int resArr[], int p3) {
        this.curArr = curArr;
        this.p1 = p1;
        this.r1 = r1;
        this.p2 = p2;
        this.r2 = r2;
        this.resArr = resArr;
        this.p3 = p3;
    }

    @Override
    public void run() {
        int n1 = r1 - p1 + 1;
        int n2 = r2 - p2 + 1;
        if (n2 > n1) {
            int t = p1;
            p1 = p2;
            p2 = t;
            t = r1;
            r1 = r2;
            r2 = t;
            t = n1;
            n1 = n2;
            n2 = t;
        }
        if (n1 == 0) {
            return;
        } else {
            int q1 = (p1 + r1) / 2;
            int q2 = binarySearch(curArr[q1], curArr, p2, r2);
            int q3 = p3 + q1 - p1 + q2 - p2;
            resArr[q3] = curArr[q1];
            MergeThread first = new MergeThread(curArr, p1,
                    q1 - 1, p2, q2 - 1, resArr, p3);
            Thread thr1 = new Thread(first);
            thr1.start();
            MergeThread second = new MergeThread(curArr,
                    q1 + 1, r1, q2, r2, resArr, q3 + 1);
            second.run();
            try {
                thr1.join();
            } catch (InterruptedException e) {
            }
        }

    }

    int binarySearch(int x, int mas[], int p, int r) {
        int low = p;
        int high = Math.max(p, r + 1);
        while (low < high) {
            int mid = (low + high) / 2;
            if (x < mas[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }
}
