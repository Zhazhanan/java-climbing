package org.youqiu.designpattern.creational.abstractfactorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * 抽象工厂模式提供一个接口，用于创建相关或依赖的对象家族，而不需要明确具体指定具体类
 * 抽象工厂模式中我们可以定义实现不止一个接口，一个工厂也可以生成不止一个产品类，抽象工厂模式较好的实现了“开放-封闭”原则
 */
public interface AbstractFactory {
    Phone makePhone();

    PC makePC();
}
