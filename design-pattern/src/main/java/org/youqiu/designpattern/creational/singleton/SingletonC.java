package org.youqiu.designpattern.creational.singleton;

/**
 * Description
 * <br> Created by WangKun on 2020/05/14
 * <br> 静态内部类实现
 **/
public class SingletonC {

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static SingletonC c = new SingletonC();
    }

    private SingletonC() {
    }

    public static SingletonC getSingletonC() {
        return SingletonHolder.c;
    }
}
