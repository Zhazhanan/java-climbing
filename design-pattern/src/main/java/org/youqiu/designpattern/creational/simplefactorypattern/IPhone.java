package org.youqiu.designpattern.creational.simplefactorypattern;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class IPhone implements Phone {
    @Override
    public void call() {
        System.out.println("IPhone Call ...");
    }
}
