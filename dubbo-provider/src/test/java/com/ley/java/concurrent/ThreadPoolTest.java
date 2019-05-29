package com.ley.java.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试Java线程池
 *
 * @see java.util.concurrent.Executor
 * @see java.util.concurrent.Executors
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.Callable
 * @see java.util.concurrent.ExecutorService
 * @see Runnable
 * @see java.util.concurrent.RunnableFuture
 **/
public class ThreadPoolTest {

    /**
     * fixed thread pool
     **/
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6,
            new ThreadFactoryBuilder().setNameFormat("fixed-pool-%d").build());


    private final ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(3,
            new ThreadFactoryBuilder().setNameFormat("schedule-pool-%d").build());


    @Test
    public void testFixedThreadPool() {
        for (int i = 0; i < 16; i++) {
            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
            });
        }

        fixedThreadPool.shutdown();
    }


    @Test
    public void testScheduleThreadPool() throws IOException {
        scheduleThreadPool.scheduleWithFixedDelay(() -> {
            System.out.println(System.currentTimeMillis());
        }, 0, 2, TimeUnit.SECONDS);
        scheduleThreadPool.shutdown();
    }


    @Test
    public void testBitOper() {
        int countBits = Integer.MAX_VALUE - 3;
        int runnable = -1 << countBits;
        String runnableBytes = Integer.toBinaryString(runnable);
        System.out.println(runnableBytes);
        String shutdownBytes = Integer.toBinaryString(0 << countBits);
        System.out.println(shutdownBytes);
        System.out.println(Integer.toBinaryString(1 << countBits));
        System.out.println(Integer.toBinaryString(2 << countBits));
    }


    @Test
    public void testFutureList() {
        List<Future<Integer>> futureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futureList.add(fixedThreadPool.submit(new ComputeTask(i)));
        }


        int futureSize = futureList.size();
        int sum = 0;
        for (int i = 0; i < futureSize; i++) {
            try {
                sum += futureList.get(i).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sum);


    }


    @Test
    public void testCompletionService() throws Exception {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(fixedThreadPool);

        for (int i = 0; i < 10; i++) {
            completionService.submit(new ComputeTask(i));
        }

        int sum = 0;
        for (int i = 0; i < 10; ++i) {
            Integer result = completionService.take().get();
            if (result != null) {
                sum += result.intValue();
            }
        }
        System.out.println(sum);
    }


    private static class ComputeTask implements Callable<Integer> {

        private Integer i;

        public ComputeTask(Integer i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            return i + 1;
        }
    }
}
