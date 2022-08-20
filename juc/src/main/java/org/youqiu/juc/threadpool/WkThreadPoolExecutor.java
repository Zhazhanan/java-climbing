package org.youqiu.juc.threadpool;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池，方便jvm追踪
 * 当任务提交给ThreadPoolExecutor
 * 线程池中，先检查核心线程数是否已经全部使用，如果没有，则交由核心线程去执行任务，如果核心线程数已经全部占用，则将任务添加到队列里面，
 * 如果队列已经占满，比较当前线程池中的线程的数量是不是已超过maximumPoolSize，如果没有超过则创建线程去执行，也就是说线程池最多可以接受多少任务呢？
 * 就是maximumPoolSize+队列的大小。当线程池中的线程的数量大于corePoolSize数量时，
 * 有空闲线程则执行回收，回收时间是keepAliveTime，单位是unit，都是初始化的时候设置的。
 *
 * @author: WangKun
 * @date: 2022/8/9
 */
public class WkThreadPoolExecutor {

    private static int corePoolSize = 4;
    private static int maximumPoolSize = 4;
    private static long keepAliveTime = 60;
    private static TimeUnit unit = TimeUnit.SECONDS;
    /**
     * //ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue
     */
    private static BlockingQueue workQueue = new ArrayBlockingQueue<>(10);
    private static ThreadFactory threadFactory = new WkThreadFactory("WK");
    /**
     * //ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * //ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * //ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     * //ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */
    private static RejectedExecutionHandler handler = new WkRejectedExecutionHandler();

    static class WkRejectedExecutionHandler implements RejectedExecutionHandler{

        @SneakyThrows
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            executor.getQueue().put(r);
        }
    }

    public static ThreadPoolExecutor init() {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}
