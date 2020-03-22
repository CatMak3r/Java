package com.catmaker.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinCalculator
 *
 * @author chenhang
 * @date 2020/3/21 10:41
 */
public class ForkJoinCalculator extends RecursiveTask<Long> {
    private long start;
    private long end;

    public ForkJoinCalculator(long start, long end) {
        this.start = start;
        this.end = end;
    }

    private static final long THRESHOLD = 10000;
    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i ++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinCalculator left = new ForkJoinCalculator(start, mid);
            // 拆分子任务，同时压入线程队列
            left.fork();
            ForkJoinCalculator right = new ForkJoinCalculator(mid + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
