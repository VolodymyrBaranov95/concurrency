package ua.yandex.prodcons.utilconcurrent;

public class Producer implements Runnable {

    private CircularBuffer queue;
    private int msgCount;

    public Producer(CircularBuffer queue, int msgCount) {
        this.queue = queue;
        this.msgCount = msgCount;
    }

    @Override
    public void run() {
        long num = Thread.currentThread().getId();
        for (int i = 0; i < msgCount; i++) {
            queue.add("From thread number: " + num + " message: " + i);
        }

    }

}
