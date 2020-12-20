package com.raindrop;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author raindrop
 * @projectName Juc
 * description: 测试写时复制list
 * @created 2020-11-29 21:18
 **/
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        for (int i = 0; i < 10; i++) {
            new Thread(testThread);
        }


    }

}

class TestThread implements Runnable {

    private CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

    {
        copyOnWriteArrayList.add("Tom");
        copyOnWriteArrayList.add("Mary");
        copyOnWriteArrayList.add("Jack");

    }


    public CopyOnWriteArrayList getCopyOnWriteArrayList() {
        return copyOnWriteArrayList;
    }


    @Override
    public void run() {
        Iterator iterator = copyOnWriteArrayList.iterator();
        while (iterator.hasNext()) {
            copyOnWriteArrayList.add("zzz");
        }
    }
}