package com.raindrop;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author raindrop
 * @projectName Juc
 * description: ABC问题
 * @created 2020-11-29 21:47
 **/
/*
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 *	如：ABCABCABC…… 依次递归
 */
public class ABCDemo {

    public static void main(String[] args) {
        AlternateDemo alternateDemo = new AlternateDemo();

        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                alternateDemo.loopA();
            }
        }, "ThreadA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                alternateDemo.loopB();
            }
        }, "ThreadB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                alternateDemo.loopC();
            }
        }, "ThreadC").start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


class AlternateDemo {
    /**
     * 旗帜变量
     **/
    private Integer flag = 1;

    private ReentrantLock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    public void loopA() {
        lock.lock();
        try {
            while (flag != 1) {
                try {
                    conditionA.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("A " + Thread.currentThread().getName());
            flag++;
            conditionB.signal();
        } finally {
            lock.unlock();
        }


    }

    public void loopB() {
        lock.lock();
        try {
            while (flag != 2) {
                try {
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("B " + Thread.currentThread().getName());
            flag++;
            conditionC.signal();
        } finally {
            lock.unlock();
        }


    }

    public void loopC() {
        lock.lock();
        try {
            while (flag != 3) {
                try {
                    conditionC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C " + Thread.currentThread().getName());
            flag = 1;
            conditionA.signal();
        } finally {
            lock.unlock();
        }


    }


}
