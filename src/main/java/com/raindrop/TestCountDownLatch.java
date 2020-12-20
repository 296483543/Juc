package com.raindrop;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * @author raindrop
 * @projectName Juc
 * description: 测试倒计时
 * @created 2020-11-29 21:31
 **/
public class TestCountDownLatch {
    public static void main(String[] args) {
        LatchDemo latchDemo = new LatchDemo();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        latchDemo.setCountDownLatch(countDownLatch);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        LinkedList<Future> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Future<Long> submit = executorService.submit(latchDemo);
            list.add(submit);
        }
        try {
            countDownLatch.await();
            for (Future future : list) {
                try {
                    System.out.println(future.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("结束看吧");
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

class LatchDemo implements Callable<Long> {
    private CountDownLatch countDownLatch;

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;
        try {
            for (int i = 0; i < 50000; i++) {
                sum += i;
            }
            System.out.println("值为" + sum);
        } finally {
            countDownLatch.countDown();
            return sum;
        }
    }
}
