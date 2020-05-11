package org.youqiu.designpattern.creational.simplefactorypattern;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>  简单工厂模式实现了生成产品类的代码跟客户端代码分离
 * 问题来了，优秀的java代码是符合“开放-封闭”原则的，也就是说对扩展开发，对修改关闭
 **/
public class SimpleFactory {

    public static Phone makePhone(String phoneType) {
        if (phoneType.equals("HuaWei")) {
            return new HuaWei();
        } else if (phoneType.equals("IPhone")) {
            return new IPhone();
        }
        return null;
    }
}
