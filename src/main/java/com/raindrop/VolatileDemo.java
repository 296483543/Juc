package com.raindrop;

/**
 * @author raindrop
 * @projectName Juc
 * description: 可见性测试机
 * @created 2020-11-29 20:21
 **/
public class VolatileDemo {
    public static void main(String[] args) {
        TreadDemo treadDemo = new TreadDemo();
      /*  ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(()->{
            treadDemo.setFlag(true);
            System.out.println("主线程修改了");
        });
        executorService.shutdown();*/
        Thread thread = new Thread(treadDemo);
        thread.start();
        while (true) {
            // System.out.println("我怎么还没看见");
            if (treadDemo.isFlag()) {
                System.out.println("我看见了,flag:" + treadDemo.isFlag());
                break;
            }
        }

    }

}

class TreadDemo implements Runnable {
    private volatile boolean flag = false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        setFlag(true);
    }
}