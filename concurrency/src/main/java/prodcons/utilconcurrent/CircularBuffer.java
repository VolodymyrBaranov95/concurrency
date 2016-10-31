
package ua.yandex.prodcons.utilconcurrent;
import java.util.concurrent.locks.*;
public class CircularBuffer {
    
    private String[] elements;
    private int head;
    private int tail;
    private int size;
    private int capacity;
    final private boolean usingLock;
    private Lock aLock = new ReentrantLock();
    private Condition aCondVar = aLock.newCondition();
    
    public CircularBuffer(int capacity, boolean toLock){
        this.elements = new String[capacity];
        for (int i = 0; i < capacity; i++) {
            elements[i] = " ";
        }
        this.capacity = capacity;
        this.usingLock = toLock;
    }
    
    public void add(String data) {
        if (usingLock) {
            aLock.lock();
            try {
                while (size == elements.length) {
                    try{
                        aCondVar.await();
                    } catch (InterruptedException e){}
                }
                elements[tail] = data;
                size++;
                tail = (tail + 1) % capacity;
                aCondVar.signalAll();
            } finally {
                aLock.unlock();
            }
        } else {
            synchronized (this) {
                while (size == elements.length) {
                    try {
                        wait();
                    } catch(InterruptedException e){}
                }
                size++;
                elements[tail = (tail + 1) % capacity] = data;
                notifyAll();
            }
            
        }
    }
    
    public String remove() throws InterruptedException{
        if (usingLock) {
            aLock.lock();
            String res = new String();
            try {
                while (size == 0) {
                    try{
                        aCondVar.await();
                    } catch (InterruptedException e){}
                }
                res = elements[head];
                head = (head + 1) % capacity;
                size--;
                aCondVar.signalAll();
            } finally {
                aLock.unlock();
                return res;
            }
        } else {
            synchronized (this) {
                while (size == 0) {
                    wait();
                }
                String res = elements[head];
                head = (head + 1) % capacity;
                size--;
                notifyAll();
                return res;
            }
        }
    } 
    
}

