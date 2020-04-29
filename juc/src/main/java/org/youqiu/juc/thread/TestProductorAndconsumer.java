package org.youqiu.juc.thread;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br>
 **/
public class TestProductorAndconsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(productor, "生产者A").start();
        new Thread(consumer, "消费者B").start();
    }
}

//店员
class Clerk {
    private int product = 0;//共享数据

    public synchronized void get() throws InterruptedException { //进货
        while (product >= 10) {
            this.wait();
            System.out.println("产品已满");
        }

        System.out.println(Thread.currentThread().getName() + ":" + (++product));
        this.notifyAll();
    }

    public synchronized void sell() throws InterruptedException {//卖货
        while (product <= 0) {
            this.wait();
            System.out.println("缺货");
        }

        System.out.println(Thread.currentThread().getName() + ":" + (--product));
        this.notifyAll();
    }
}

//生产者
class Productor implements Runnable {
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                clerk.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                clerk.sell();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}