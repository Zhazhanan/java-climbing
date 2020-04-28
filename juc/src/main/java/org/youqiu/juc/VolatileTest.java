package org.youqiu.juc;

/**
 * Description
 * <br> Created by WangKun on 2020/04/28
 * <br>
 **/
public class VolatileTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            Boolean flag = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println("修改后的flag----------------" + flag);
        });
    }
}
