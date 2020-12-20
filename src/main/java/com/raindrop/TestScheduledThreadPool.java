package com.raindrop;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author raindrop
 * @projectName Juc
 * description: 延迟定时任务池
 * @created 2020-11-29 22:38
 **/
public class TestScheduledThreadPool {

    public static void main(String[] args) {
        /*ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i <5; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduledExecutorService.schedule(() -> {
                System.out.println("我延迟执行了");
            }, 5, TimeUnit.SECONDS);

        }
        scheduledExecutorService.shutdown();*/

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 30, 20, TimeUnit.MINUTES, new LinkedBlockingDeque<>(15));

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("pool-" + UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName());
            }
        });

        threadPoolExecutor.shutdown();

    }
}
