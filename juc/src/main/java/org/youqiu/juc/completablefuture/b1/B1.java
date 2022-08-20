package org.youqiu.juc.completablefuture.b1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.youqiu.juc.completablefuture.BusinessServiceTask;
import org.youqiu.juc.threadpool.WkMonitorThreadPool;
import org.youqiu.juc.threadpool.WkMonitorThreadPoolStatisticsExecutor;
import org.youqiu.juc.threadpool.WkThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: WangKun
 * @date: 2022/8/9
 */
public class B1 {
    private static Logger log = LoggerFactory.getLogger(B1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        StopWatch watch = new StopWatch();
        watch.start();
        ThreadPoolExecutor pool = WkThreadPoolExecutor.init();
        WkMonitorThreadPool wkMonitorThreadPool = new WkMonitorThreadPool(pool,2);

        int corePoolSize = 4;
        int maximumPoolSize= 8;
        long keepAliveTime= 60;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(60);
                String poolName = "WK-monitor";
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        long timeOut = 10000L;
        long execTimeout =10000L;
        long waitInQueueTimeout =10000L;
        long queueSizeWarningPercent = 70L;
        WkMonitorThreadPoolStatisticsExecutor executor = new WkMonitorThreadPoolStatisticsExecutor( corePoolSize,  maximumPoolSize,  keepAliveTime,  unit,  workQueue,
                 poolName,  timeOut,  execTimeout,  waitInQueueTimeout,  queueSizeWarningPercent);

        for (int i = 0; i < 100; i++) {
            executor.execute(()->{
                long id = Thread.currentThread().getId();
                try
                {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("doSomething:: Thread id is {}, name is {}", id,Thread.currentThread().getName());
            });
        }

        List<CompletableFuture<Long>> list = new ArrayList();
       /* for (int i = 0; i < 100; i++) {
            final int finalI = i;
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(()->{
                long id = Thread.currentThread().getId();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("doSomething:: Thread id is {}, name is {}", id,Thread.currentThread().getName());
                return id;
            }, pool);
            list.add(future);
//            log.info("活跃的线程数：" + pool.getActiveCount() + ",核心线程数：" + pool.getCorePoolSize() + ",线程池大小：" + pool.getPoolSize() + ",队列的大小" + pool.getQueue().size());
        }
        wkMonitorThreadPool.run();

        List<Long> resultList = new ArrayList();
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
        all.get();*/

        watch.stop();
//        log.info("main:: run {}, resultList:{}",watch.getTotalTimeSeconds(), resultList);

    }
}
