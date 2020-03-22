package com.catmaker.forkjoin;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * ForkJoinTest
 *
 * @author chenhang
 * @date 2020/3/21 10:51
 */
public class ForkJoinTest {
    /**
     * ForkJoin 框架
     */
    @Test
    public void test() {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculator(0, 100000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间:" + Duration.between(start, end).toMillis());
        // 耗费时间:12410
    }

    @Test
    public void test2() {
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i < 100000000001L; i ++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间:" + Duration.between(start, end).toMillis());
        // 耗费时间:25902
    }

    /**
     * java8 并行流
     */
    @Test
    public void test3() {
        Instant start = Instant.now();
        LongStream.rangeClosed(0, 100000000000L)
                .parallel()
                .reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("耗费时间:" + Duration.between(start, end).toMillis());
        // 耗费时间:7433
    }
}
