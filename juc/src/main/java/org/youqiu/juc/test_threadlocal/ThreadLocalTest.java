package org.youqiu.juc.test_threadlocal;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description
 * <br> Created by WangKun on 2020/05/13
 * <br>
 **/
public class ThreadLocalTest {

    static class NumUtil {
        public static int addNum = 0;

        private static ThreadLocal<Integer> addNumThreadLocal = new ThreadLocal<>();

        public static int add10(int num) {
            addNum = num;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return addNum + 10;
        }

        public static int add(int num) {
            addNum = num;
            addNumThreadLocal.set(addNum);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return addNumThreadLocal.get() + 10;
        }
    }

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            int num = i;
            service.execute(() -> {
//                System.out.println(num + " " + NumUtil.add10(num));
                System.out.println(num + " " + NumUtil.add(num));
            });
        }
        service.shutdown();
    }

}
