package org.youqiu.designpattern.creational.singleton;

/**
 * 单例模式 确保一个类只有一个实例，并提供一个全局访问方法
 */
public class Singleton {
    private volatile static Singleton uniqueSingleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (uniqueSingleton == null) {
            synchronized (Singleton.class) {
                if (uniqueSingleton == null) {
                    uniqueSingleton = new Singleton();
                }
            }
        }
        return uniqueSingleton;
    }
}
