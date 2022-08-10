package org.youqiu.juc.completablefuture.b1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youqiu.juc.completablefuture.BusinessServiceTask;
import org.youqiu.juc.threadpool.WkThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: WangKun
 * @date: 2022/8/9
 */
public class B1 {
    private static Logger log = LoggerFactory.getLogger(B1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = WkThreadPoolExecutor.init();
        List<CompletableFuture<Integer>> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> new BusinessServiceTask().doSomething(finalI), pool);
            list.add(future);
            log.info("活跃的线程数：" + pool.getActiveCount() + ",核心线程数：" + pool.getCorePoolSize() + ",线程池大小：" + pool.getPoolSize() + ",队列的大小" + pool.getQueue().size());
        }


        List<Integer> resultList = new ArrayList();
        CompletableFuture<Void> all = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]))
                .whenComplete((v, e) -> {
                    list.forEach(val -> {
                        try {
                            resultList.add(val.get());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    });
                });
        all.get();
        log.info("main:: resultList:{}", resultList);
    }
}
