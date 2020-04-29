package org.youqiu.juc.test_volatile;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br>
 **/
public class VolatileVar {
    //    int number = 0;
    volatile int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void add(int i) {
        number = number + i;
    }
}
