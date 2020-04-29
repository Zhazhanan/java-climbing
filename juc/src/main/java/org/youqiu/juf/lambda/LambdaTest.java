package org.youqiu.juf.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br>
 **/
public class LambdaTest {
    /**
     * 无参数
     */
    Runnable runnable = () -> System.out.println("hello Lambda");
    /**
     * 一个参数
     */
    Consumer<String> con = (t) -> System.out.println(t);
    /**
     * 参数省略括号
     */
    Consumer<String> con2 = t -> System.out.println(t);

    /**
     * 两个参数，有返回值
     */
    Comparator<Integer> comparator = (x, y) -> {
        System.out.println("相加结果是:" + (x + y));
        return Integer.compare(x, y);
    };

    /**
     * 省略return和大括号
     */
    Comparator<Integer> comparator2 = (x, y) -> Integer.compare(x, y);


    public static void main(String[] args) {
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        double allCost = cost.stream().reduce((sum, x) -> sum + x).get();
        System.out.println(allCost);

        List<Integer> numbers = Stream.iterate(1, x -> x + 1).limit(10).collect(
                Collectors.toList());
        IntStream.rangeClosed(0, 10).boxed().collect(Collectors.toList());
        numbers.forEach(System.out::print);
    }
}
