package org.youqiu.designpattern.creational.abstractfactorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class Test {
    public static void main(String[] args) {
        AbstractFactory factory;
        factory = new HuaWeiPCFactory();
        PC pc = factory.makePC();
        Phone phone = factory.makePhone();
        pc.watch();
        phone.call();

        factory = new XiaoMiPCFactory();
        PC xmPc = factory.makePC();
        Phone xmP = factory.makePhone();
        xmPc.watch();
        if (null != xmP) {
            xmP.call();
        }
    }
}
