package org.youqiu.juc.test_volatile;

/**
 * Description
 * <br> Created by WangKun on 2020/04/28
 * <br>
 **/
public class VolatileTest {

    /*验证volatile的可见性*/
    public static void main(String[] args) {
        VolatileVar volatileVar = new VolatileVar();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t come in");
                volatileVar.add(100);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("修改后的number----------------" + volatileVar.getNumber());
        }).start();

        while (volatileVar.number == 0) {

        }

        System.out.println(Thread.currentThread().getName() + " it is over");
    }
}
