package com.raindrop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author raindrop
 * @projectName Juc
 * description: 测试原子性
 * @created 2020-11-29 20:41
 **/
public class AtomicDemo {

    public static void main(String[] args) {
        Atomic atomic = new Atomic();


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(atomic);
        }
       /* for (int i = 0; i < 50; i++) {
            new Thread(()->{
                atomic.add();
            }).start();
        }*/

        executorService.shutdown();

    }

}

class Atomic implements Runnable {
    ReentrantLock lock = new ReentrantLock();
    /**
     * 测试原子性
     **/
    private volatile int iii = 0;

    public Integer getIii() {
        return iii;
    }

    public void setIii(Integer iii) {
        this.iii = iii;
    }


    public void add() {
        lock.lock();
        try {
            iii++;
            System.out.println("值为" + getIii());
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void run() {
        add();
    }
}
