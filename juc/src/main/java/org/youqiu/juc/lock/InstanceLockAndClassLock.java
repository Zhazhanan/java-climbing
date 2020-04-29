package org.youqiu.juc.lock;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br> 类锁与对象锁
 **/
public class InstanceLockAndClassLock {

    //  类锁
    public void classLock() {
        synchronized (InstanceLockAndClassLock.class) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    //    类锁
    public static synchronized void classLock2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    //    对象锁
    public void instanceLock() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    //    对象锁
    public synchronized void instanceLock2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    public static void main(String[] args) {
        final InstanceLockAndClassLock myt2 = new InstanceLockAndClassLock();

        // 测试类锁
        new Thread(() -> myt2.classLock(), "test1").start();
        new Thread(() -> InstanceLockAndClassLock.classLock2(), "test2").start();

        // 测试对象锁
//        new Thread(() -> myt2.instanceLock(), "test3").start();
//        new Thread(() -> myt2.instanceLock2(), "test4").start();

        // 测试类锁与对象锁
//        new Thread(() -> myt2.classLock(), "test5").start();
//        new Thread(() -> myt2.instanceLock(), "test6").start();
    }
}
