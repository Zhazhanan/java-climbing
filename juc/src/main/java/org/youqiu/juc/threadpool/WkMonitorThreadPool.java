package org.youqiu.juc.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池监控
 *
 * @author: WangKun
 * @date: 2022/8/19
 */
public class WkMonitorThreadPool implements Runnable{
    private static Logger log = LoggerFactory.getLogger(WkMonitorThreadPool.class);

    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean isRun =  true;

    public void shutdown(){
        isRun = false;
    }

    public WkMonitorThreadPool(ThreadPoolExecutor executor, int seconds) {
        this.executor = executor;
        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (isRun){
            log.info("[monitor]:: 线程池大小:{} 核心数:{} 活跃数:{} 完成数:{} 任务数:{} 线程结束:{} 任务结束:{}",
                    this.executor.getPoolSize(),this.executor.getCorePoolSize(),this.executor.getActiveCount(),
                    this.executor.getCompletedTaskCount(),this.executor.getTaskCount(),
                    this.executor.isShutdown(),this.executor.isTerminated());
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
