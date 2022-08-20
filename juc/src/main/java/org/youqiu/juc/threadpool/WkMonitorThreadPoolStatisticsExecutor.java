package org.youqiu.juc.threadpool;

import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 带监控线程池
 *
 * activeCount	活跃线程数
 * corePoolSize	核心线程数
 * poolSize	当前线程池中运行的线程总数
 * maximumPoolSize	最大线程数
 * taskCount	已提交还未执行的任务数
 * completedTaskCount	线程池已经执行完成的任务总数
 * largestPoolSize	线程池容量触达过的最大值
 * rejectCount	被拒绝的线程的数量
 * queueSize/waitTaskCount	等待执行的任务数量
 * taskAvgTime	任务平均时长(以任务提交时间进行计时)
 * taskAvgExecTime	任务平均执行时长(以任务开始执行时间进行计时)
 * taskTotalTime	任务总时长(以任务提交时间进行计时)
 * taskTotalExecTime	任务执行时长(以任务开始执行时间进行计时)
 * minTaskTime	最短任务时间(以任务提交时间进行计时)
 * maxTaskTime	最长任务时间(以任务提交时间进行计时)
 *
 *
 * @author: WangKun
 * @date: 2022/8/20
 */
@Getter
public class WkMonitorThreadPoolStatisticsExecutor extends ThreadPoolExecutor implements DisposableBean {
    private static Logger log = LoggerFactory.getLogger(WkMonitorThreadPoolStatisticsExecutor.class);

    // 线程池名称
    private String poolName;
    // 任务超时阀值，便于统计
    private Long timeOut = 120000L;
    // 是否记录任务超时次数
    private boolean taskTimeoutFlag = false;
    // 任务执行超时时间阀值，便于统计
    private Long execTimeout = 120000L;
    // 是否记录任务执行超时次数
    private boolean taskExecTimeoutFlag = false;
    // 任务在队列中等待的时间阀值，便于统计
    private long waitInQueueTimeout = 60000L;
    // 是否记录任务等待超时次数
    private boolean taskWaitInQueueTimeoutFlag = false;
    // 任务队列使用率告警阀值
    private long queueSizeWarningPercent = 80;
    // 是否进行队列容量告警
    private boolean queueSizeWarningFlag = false;
    // 队列是否达到过预警阀值
    private AtomicBoolean queueSizeHasWarningFlag = new AtomicBoolean(false);
    // 任务总时长，便于统计。以任务提交时进行计时，单位ms
    private AtomicLong taskTotalTime = new AtomicLong(0);
    // 任务总执行时长，用于统计。以任务开始执行计时，单位ms
    private AtomicLong taskTotalExecTime = new AtomicLong(0);
    // 最短任务时长，以任务提交时计时，单位ms
    private long minTaskTime = Long.MAX_VALUE;
    // 最长任务时长，以任务提交时计时，单位ms
    private long maxTaskTime = 0;
    // 任务超时次数，以提交任务进行计时
    private AtomicLong taskTimeoutCount = new AtomicLong(0);
    // 任务执行超时次数，以任务开始执行计时
    private AtomicLong taskExecTimeoutCount = new AtomicLong(0);
    // 任务等待时间超过设定阀值的次数
    private AtomicLong taskWaitInQueueTimeoutCount = new AtomicLong(0);
    // 最短任务执行时间，以任务执行时间计时，单位ms
    private long minTaskExecTime = Long.MAX_VALUE;
    // 最长任务执行时间，以任务执行时间计时，单位ms
    private long maxTaskExecTime = 0;
    // 保存任务信息
    private Map<String, TaskStatistics> taskInfoMap = new ConcurrentHashMap();

    static class WkRejectedExecutionHandler implements RejectedExecutionHandler{

        @SneakyThrows
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            executor.getQueue().put(r);
        }
    }


    public WkMonitorThreadPoolStatisticsExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                                                 String poolName, long timeOut, long execTimeout, long waitInQueueTimeout, long queueSizeWarningPercent) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new WkThreadFactory(poolName), new WkRejectedExecutionHandler());
        this.poolName = poolName;
        this.timeOut = timeOut;
        this.execTimeout = execTimeout;
        this.waitInQueueTimeout = waitInQueueTimeout;
        this.queueSizeWarningPercent = queueSizeWarningPercent;
        if(this.timeOut > 0)this.taskTimeoutFlag = true;
        if (this.execTimeout > 0)this.taskExecTimeoutFlag = true;
        if(this.waitInQueueTimeout > 0)this.taskWaitInQueueTimeoutFlag = true;
        if (this.queueSizeWarningPercent > 0)this.queueSizeWarningFlag = true;
    }

    public WkMonitorThreadPoolStatisticsExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler,
                                                 String poolName, long timeOut, long execTimeout, long waitInQueueTimeout, long queueSizeWarningPercent) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new WkThreadFactory(poolName), handler);
        this.poolName = poolName;
        this.timeOut = timeOut;
        this.execTimeout = execTimeout;
        this.waitInQueueTimeout = waitInQueueTimeout;
        this.queueSizeWarningPercent = queueSizeWarningPercent;
        if(this.timeOut > 0)this.taskTimeoutFlag = true;
        if (this.execTimeout > 0)this.taskExecTimeoutFlag = true;
        if(this.waitInQueueTimeout > 0)this.taskWaitInQueueTimeoutFlag = true;
        if (this.queueSizeWarningPercent > 0)this.queueSizeWarningFlag = true;
    }

    @Override
    public void execute(Runnable command) {
        String s = String.valueOf(command.hashCode());
        this.taskInfoMap.put(s,new TaskStatistics());
        if (this.queueSizeWarningFlag){
            float f = (float)getQueue().size() / (getQueue().size() + getQueue().remainingCapacity());
            BigDecimal bd= new BigDecimal(f).setScale(2, RoundingMode.HALF_UP);
            int usePercent = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue();
            if (usePercent > queueSizeWarningPercent){
                this.queueSizeHasWarningFlag.set(true);
                log.warn("QueueSize percent Warning! used: {}%, qSize: {}, remaining: {}",usePercent,getQueue().size(),getQueue().remainingCapacity());
            }
        }
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String s = String.valueOf(r.hashCode());
        TaskStatistics taskStatistics = this.taskInfoMap.get(s);
        if (!Objects.isNull(taskStatistics)) {
            taskStatistics.setStartExecTime(System.currentTimeMillis());
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String s = String.valueOf(r.hashCode());
        long endTime = System.currentTimeMillis();
        TaskStatistics taskStatistics = this.taskInfoMap.remove(s);
        if (!Objects.isNull(taskStatistics)) {
            System.out.println("s-----------2-----------------"+taskStatistics);
            long taskTotalTime = endTime - taskStatistics.getCommitTime();
            long taskExecTime = endTime - taskStatistics.getStartExecTime();
            long taskWaitInQueueTime = taskStatistics.getStartExecTime() - taskStatistics.getCommitTime();
            this.taskTotalTime.addAndGet(taskTotalTime);
            this.taskTotalExecTime.addAndGet(taskExecTime);
            if (this.minTaskTime > taskTotalTime)this.minTaskTime = taskTotalTime;
            if (this.maxTaskTime < taskTotalTime)this.maxTaskTime = taskTotalTime;
            if (this.taskTimeoutFlag && taskTotalTime > this.timeOut) this.taskTimeoutCount.incrementAndGet();
            if(this.minTaskExecTime > taskExecTime)this.minTaskExecTime = taskExecTime;
            if (this.maxTaskExecTime < taskExecTime)this.maxTaskExecTime = taskExecTime;
            if (this.taskExecTimeoutFlag && taskExecTime > this.execTimeout) this.taskExecTimeoutCount.incrementAndGet();
            if (this.taskWaitInQueueTimeoutFlag && taskWaitInQueueTime > this.waitInQueueTimeout)
                this.taskWaitInQueueTimeoutCount.incrementAndGet();
            log.warn("Task cost info[taskTotalTime:{}, taskExecTime:{}, taskWaitInQueueTime:{}，taskStatistics.getStartExecTime(){}, taskStatistics.getCommitTime(){}] ", taskTotalTime, taskExecTime,taskWaitInQueueTime,taskStatistics.getStartExecTime(),taskStatistics.getCommitTime());
        }
        super.afterExecute(r, t);

    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }

    /**
     *  任务平均时长，无任务时返回0
     *
     * @param
     * @return {@link {@link long}}
     * @author Wangkun
     * @date 2022/8/20 23:16
     */
    public long getTaskAvgTime() {
        if (this.getCompletedTaskCount() > 0)return this.getTaskTotalTime().get()/this.getCompletedTaskCount();
        return 0;
    }

    /**
     *  任务平均执行时间，无任务时返回0
     *
     * @param
     * @return {@link {@link long}}
     * @author Wangkun
     * @date 2022/8/20 23:17
     */
    public long getTaskAvgExecTime() {
        if (this.getCompletedTaskCount() > 0)return this.getTaskTotalExecTime().get()/this.getCompletedTaskCount();
        return 0;
    }
}
