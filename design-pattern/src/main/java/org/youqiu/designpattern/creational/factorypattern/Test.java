package org.youqiu.designpattern.creational.factorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class Test {
    public static void main(String[] args) {
        Factory hwFactory = new HuaWeiFactory();
        IPhoneFactory iPhoneFactory = new IPhoneFactory();
        Phone phone = hwFactory.makePhone();
        Phone phone1 = iPhoneFactory.makePhone();
        phone.call();
        phone1.call();
    }
}
