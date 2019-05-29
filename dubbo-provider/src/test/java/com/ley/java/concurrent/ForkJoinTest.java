package com.ley.java.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {


    private static class CountTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD = 500; // 阈值
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            int sum = 0;

            //如果足够小就计算任务
            boolean canCompute = (end - start) <= THRESHOLD;


            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //// 如果任务大于阈值,就分裂成两个子任务计算
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle, end);

                //执行子任务
                leftTask.fork();
                rightTask.fork();

                //等待子任务执行完,并得到计算结果
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();

                //合并子任务
                sum = leftResult + rightResult;

            }

            return sum;
        }
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();

        // 生成一个计算任务，负责计算1+2+3+...+1000
        CountTask task = new CountTask(1, 10000);

        // 执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
