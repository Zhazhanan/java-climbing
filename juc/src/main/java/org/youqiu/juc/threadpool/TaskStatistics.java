package org.youqiu.juc.threadpool;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 线程任务统计
 *
 * @author: WangKun
 * @date: 2022/8/20
 */
@Getter
@Setter
@ToString
public class TaskStatistics {
    private long commitTime;
    private long startExecTime;

    public TaskStatistics() {
        this.commitTime = System.currentTimeMillis();
    }
}
