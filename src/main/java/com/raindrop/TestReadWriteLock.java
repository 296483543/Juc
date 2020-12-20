package com.raindrop;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author raindrop
 * @projectName Juc
 * description: 读写锁测试
 * @created 2020-11-29 22:20
 **/
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                readWriteLockDemo.setFlag(new Random().nextInt(101));
            }).start();
        }

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                readWriteLockDemo.getFlag();
            }).start();
        }
    }
}

class ReadWriteLockDemo {

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private int flag = 0;


    public int getFlag() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("读" + Thread.currentThread().getName() + ":" + flag);
            return flag;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void setFlag(int flag) {

        readWriteLock.writeLock().lock();
        try {
            this.flag = flag;
            System.out.println("写" + Thread.currentThread().getName() + ":" + this.flag);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
