package com.ruan.yuanyuan.thread.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ClassName TestThreadPool
 * @Author ruanyuanyuan
 * @Date 2020/10/12-08:47
 * @Version 1.0
 * @Description TODO 线程池
 **/
public class TestThreadPool {

    public static void main(String[] args) {

        //固定线程池，核心线程数和最大线程数一样，说明任务再多线程也不会超过传入的线程数量，适合任务量小
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        //缓存线程池，核心线程池为0，最大线程数为Integer的最大值，适合任务量大
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //定时线程池，主要用于定时执行任务
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(9);
        //单例线程池，核心线程数和最大线程池数只有一个，就算出现异常还是只有一个线程
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


    }
}
