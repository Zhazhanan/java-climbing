package org.youqiu.designpattern.creational.factorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br> 工厂模式 定义了一个创建对象的接口，但由子类决定要实例化哪个类。工厂方法让类把实例化推迟到子类
 **/
public interface AbstractFactory {
    Phone makePhone();
}
