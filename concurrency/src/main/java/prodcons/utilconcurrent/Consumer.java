/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.yandex.prodcons.utilconcurrent;

public class Consumer implements Runnable{
    private CircularBuffer queue;
    private int msgCount;
    
    public Consumer(CircularBuffer queue, int msgCount) {
        this.queue = queue;
        this.msgCount = msgCount;
    }
    
    @Override
    public void run(){
        long num = Thread.currentThread().getId();
        try{
            for (int i = 0; i <msgCount; i++){
                String res = queue.remove();
            }
        } catch (InterruptedException e) {}
       
    }
}

