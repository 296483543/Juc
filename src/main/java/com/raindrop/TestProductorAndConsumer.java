package com.raindrop;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author raindrop
 * @projectName Juc
 * description: 生产者和消费者案例
 * @created 2020-11-29 23:06
 **/
public class TestProductorAndConsumer {
    public static void main(String[] args) {
        Cleck cleck = new Cleck();
        Productor productor = new Productor(cleck);
        Consumer consumer = new Consumer(cleck);
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
class Cleck {

    /**
     * 商品
     **/
    private Integer Product = 0;

    /**
     * @return void
     * @author raindrop
     * description: 生产
     * @date 2020/11/29 23:14
     **/
    public synchronized void add() {
        while (Product >= 10) {
            try {
                System.out.println("商品已满");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product++;
        System.out.println(Thread.currentThread().getName() + "生产者" + "商品数量:" + getProduct());
        this.notifyAll();
    }

    /**
     * @return void
     * @author raindrop
     * description:消费
     * @date 2020/11/29 23:17
     **/
    public synchronized void jian() {

        while (Product <= 0) {
            try {
                System.out.println("商品已无货");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product--;
        System.out.println(Thread.currentThread().getName() + "消费者" + "商品数量:" + getProduct());
        this.notifyAll();
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
class Productor implements Runnable {
    private Cleck cleck;

    public Productor(Cleck cleck) {
        this.cleck = cleck;
    }


    @Override
    public void run() {
        cleck.add();
    }
}

/**
 * @author raindrop
 * description:  消费者
 * @date 2020/11/29 23:09
 **/
class Consumer implements Runnable {
    private Cleck cleck;

    public Consumer(Cleck cleck) {
        this.cleck = cleck;
    }

    @Override
    public void run() {
        cleck.jian();
    }
}