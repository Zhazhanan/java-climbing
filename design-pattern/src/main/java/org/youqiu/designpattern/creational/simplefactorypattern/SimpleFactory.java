package org.youqiu.designpattern.creational.simplefactorypattern;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
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
