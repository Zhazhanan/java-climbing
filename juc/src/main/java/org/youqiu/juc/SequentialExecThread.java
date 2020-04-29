package org.youqiu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br> 实现目标，线程按顺序执行
 **/
public class SequentialExecThread extends Thread {
    int i;
    //上一个线程
    Thread previousThread;

    public SequentialExecThread(Thread previousThread, int i) {
        this.previousThread = previousThread;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            //调用上一个线程的join方法，大家可以自己演示的时候可以把这行代码注释掉
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num:" + i);
    }

    public static void main(String[] args) throws InterruptedException {
        // 通过countdownlatch实现
        comeByCountDownLatch();
        /// 通过thread.join实现
        comeByJoin();
    }

    public static void comeByJoin() {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            SequentialExecThread joinDemo = new SequentialExecThread(previousThread, i);
            joinDemo.start();
            previousThread = joinDemo;
        }
    }

    public static void comeByCountDownLatch() throws InterruptedException {
        for (int j = 0; j < 10; j++) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            final int i = j;
            new Thread(() -> {
                System.out.println("-----------" + i);
                countDownLatch.countDown();
            }).start();
            countDownLatch.await();
        }
    }
}
