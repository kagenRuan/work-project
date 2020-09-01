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

        new Thread(() ->{
            try {
                System.out.println(Thread.currentThread().getName()+"开始");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();

            }
        },"1").start();;

        new Thread(() ->{
            try {
                System.out.println(Thread.currentThread().getName()+"开始");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        },"2").start();;

        new Thread(() ->{
            try {
                System.out.println(Thread.currentThread().getName()+"开始");
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        },"3").start();;


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }
}
