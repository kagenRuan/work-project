package com.ruan.yuanyuan.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有线程都已经执行完成");
            }
        });

        new Thread(()->{
            try {
                System.out.println("线程A执行任务完成");
                cyclicBarrier.await();
                System.out.println("线程A执行任务完成退出。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            try {
                System.out.println("线程B执行任务完成");
                cyclicBarrier.await();
                System.out.println("线程B执行任务完成退出。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                System.out.println("线程C执行任务完成");
                cyclicBarrier.await();
                System.out.println("线程C执行任务完成退出。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        },"C").start();
    }
}
