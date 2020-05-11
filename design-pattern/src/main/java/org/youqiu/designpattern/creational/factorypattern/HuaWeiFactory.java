package org.youqiu.designpattern.creational.factorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.HuaWei;
import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new HuaWei();
    }
}
