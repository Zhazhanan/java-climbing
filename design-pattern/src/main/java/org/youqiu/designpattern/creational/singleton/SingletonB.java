package org.youqiu.designpattern.creational.singleton;

/**
 * Description
 * <br> Created by WangKun on 2020/05/14
 * <br> 饿汉模式
 **/
public class SingletonB {
    private final static SingletonB b = new SingletonB();

    private SingletonB() {
    }

    public static SingletonB getSingletonB() {
        return b;
    }
}
