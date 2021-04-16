package com.ruan.yuanyuan.lock;

import javax.xml.bind.SchemaOutputResolver;
import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->{
            System.out.println("ThreadA执行");
            countDownLatch.countDown();
        },"ThreadA").start();
        new Thread(()->{
            System.out.println("ThreadB执行");
            countDownLatch.countDown();
        },"ThreadB").start();

        countDownLatch.await();
        System.out.println("所有的任务都已经执行完成");
    }
}
