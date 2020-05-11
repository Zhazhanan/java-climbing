package org.youqiu.designpattern.creational.abstractfactorypattern;

import org.youqiu.designpattern.creational.simplefactorypattern.Phone;

/**
 * Description
 * <br> Created by WangKun on 2020/05/11
 * <br>
 **/
public class XiaoMiPCFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return null;
    }

    @Override
    public PC makePC() {
        return new XiaoMiPC();
    }
}
