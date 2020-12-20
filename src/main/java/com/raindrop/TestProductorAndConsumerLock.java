package com.raindrop;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author raindrop
 * @projectName Juc
 * description: Lock的生产消费案例
 * @created 2020-11-29 23:27
 **/
public class TestProductorAndConsumerLock {
    public static void main(String[] args) {
        Cleck1 cleck = new Cleck1();
        Productor1 productor = new Productor1(cleck);
        Consumer1 consumer = new Consumer1(cleck);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.MINUTES, new LinkedBlockingDeque<>(15));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(productor);
            threadPoolExecutor.submit(consumer);

        }

        threadPoolExecutor.shutdown();
    }
}

/**
 * @author raindrop
 * description:  店长
 * @date 2020/11/29 23:09
 **/
class Cleck1 {

    /**
     * 商品
     **/
    private Integer Product = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * @return void
     * @author raindrop
     * description: 生产
     * @date 2020/11/29 23:14
     **/
    public void add() {
        lock.lock();
        try {
            while (Product >= 10) {
                try {
                    System.out.println("商品已满");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Product++;
            System.out.println(Thread.currentThread().getName() + "生产者" + "商品数量:" + getProduct());
            condition.signalAll();
        } finally {
            lock.unlock();
        }


    }

    /**
     * @return void
     * @author raindrop
     * description:消费
     * @date 2020/11/29 23:17
     **/
    public void jian() {
        lock.lock();
        try {
            while (Product <= 0) {
                try {
                    System.out.println("商品已无货");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Product--;
            System.out.println(Thread.currentThread().getName() + "消费者" + "商品数量:" + getProduct());
            condition.signalAll();
        } finally {
            lock.unlock();
        }


    }


    public Integer getProduct() {
        return Product;
    }

}

/**
 * @author raindrop
 * description:  生产者
 * @date 2020/11/29 23:09
 **/
class Productor1 implements Runnable {
    private Cleck1 cleck1;

    public Productor1(Cleck1 cleck) {
        this.cleck1 = cleck;
    }


    @Override
    public void run() {
        cleck1.add();
    }
}

/**
 * @author raindrop
 * description:  消费者
 * @date 2020/11/29 23:09
 **/
class Consumer1 implements Runnable {
    private Cleck1 cleck1;

    public Consumer1(Cleck1 cleck) {
        this.cleck1 = cleck;
    }

    @Override
    public void run() {
        cleck1.jian();
    }
}
