package com.ruan.yuanyuan;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 拿到初始值，进行加法，加到指定的值后执行下一步
 */
public class TestCyclicBarrier {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("########最终都已完成##########");
        });


        for (int i = 0; i < 7; i++) {
            String index = String.valueOf(i);
            new Thread(() -> {
                try {
                    System.out.println("已完成第" + index + "次");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, index).start();
        }

    }
}
