package org.youqiu.juc.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br> “分而治之”的算法，特别是分治之后递归调用的函数，例如 quick sort 等
 * 适合的是计算密集型的任务，如果存在 I/O，线程间同步，sleep() 等会造成线程长时间阻塞的情况时，最好配合使用 ManagedBlocker
 * 下面demo求和
 **/
public class SumCalculatorByForkJoin extends RecursiveTask<Long> {

    //要求和的数组
    private final long[] numbers;
    //子任务处理的数组开始和终止的位置
    private final int start;
    private final int end;

    //不在将任务分解成子任务的阀值大小
    public static final int THRESHOLD = 10000;

    //用于创建组任务的构造函数
    public SumCalculatorByForkJoin(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    //用于递归创建子任务的构造函数
    public SumCalculatorByForkJoin(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        //当前任务负责求和的部分的大小
        int length = end - start;
        //如果小于等于阀值就顺序执行计算结果
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        //创建子任务来为数组的前一半求和
        SumCalculatorByForkJoin leftTask = new SumCalculatorByForkJoin(numbers, start, start + length / 2);
        //创建子任务来为数组的后一半求和
        SumCalculatorByForkJoin rightTask = new SumCalculatorByForkJoin(numbers, start + length / 2, end);

        invokeAll(leftTask, rightTask);
        //合并两个子任务的计算结果
        return rightTask.join() + leftTask.join();
    }

    //顺序执行计算的简单算法
    private long computeSequentially() {
//        long sum = 0;
//        for (int i = start; i < end; i++) {
//            sum += numbers[i];
//        }
//        return sum;
        return Arrays.stream(numbers).parallel().reduce(0, Long::sum);
//        return Arrays.stream(numbers).reduce((a, b) -> a + b).getAsLong();
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();
        ForkJoinTask<Long> task = new SumCalculatorByForkJoin(numbers);
        Long a = new ForkJoinPool().invoke(task);
        System.out.println(a);
    }
}
