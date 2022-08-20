package org.youqiu.juc.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;


/**
 * 业务类，处理业务逻辑
 *
 * @author: WangKun
 * @date: 2022/8/9
 */
public class BusinessServiceTask implements Callable {
    private static Logger log = LoggerFactory.getLogger(BusinessServiceTask.class);

  /*  // do something
    public Integer doSomething(Integer i) {
        long id = Thread.currentThread().getId();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("doSomething:: i:{},Thread id is {}, name is {}", i, id,Thread.currentThread().getName());
        return i;
    }*/

    @Override
    public Object call() throws Exception {
        long id = Thread.currentThread().getId();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("doSomething:: Thread id is {}, name is {}", id,Thread.currentThread().getName());
        return id;
    }
}
