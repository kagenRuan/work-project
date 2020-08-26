package com.ruan.yuanyuan.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName TestCountDownLatch
 * @Author ruanyuanyuan
 * @Date 2020/8/17-16:24
 * @Version 1.0
 * @Description TODO  测试CountDownLatch
 **/
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i <3 ; i++) {
            new Thread(() ->{
                try {
                    System.out.println(Thread.currentThread().getName()+"开始");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            },i+"").start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }
}
