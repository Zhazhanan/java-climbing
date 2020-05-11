package org.youqiu.designpattern.creational.factorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br> 工厂模式 定义了一个创建对象的接口，但由子类决定要实例化哪个类。工厂方法让类把实例化推迟到子类
 * 实现“开发-封闭”原则，无论加多少产品类，我们都不用修改原来类中的代码，而是通过增加工厂类来实现
 * 缺点是，如果产品类过多，我们就要生成很多的工厂类
 **/
public interface Factory {
    Phone makePhone();
}
