package org.youqiu.designpattern.creational.simplefactorypattern;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class Test {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
//        simpleFactory.
        Phone huaWei = SimpleFactory.makePhone("HuaWei");
        Phone iPhone = SimpleFactory.makePhone("IPhone");
        huaWei.call();
        iPhone.call();

    }
}
