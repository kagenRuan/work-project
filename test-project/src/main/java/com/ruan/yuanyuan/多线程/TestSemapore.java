package com.ruan.yuanyuan.多线程;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号
 * 下面举例说明：
 * 描述：有三个车位，6个用户抢车位。
 * 1.第一次只有6个用户中的三个能抢到车位
 * 2.当抢到车位的三个用户离开后其他三个用户才能开始抢剩余的车位
 * 这就是信号量，其实也就是只有收到信号后用户才能开始抢占资源
 */
public class TestSemapore {


    public static void main(String[] args) {
        //车位3个
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            String index = String.valueOf(i);
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("#########" + index + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.out.println("#########" + index + "离开车位车位");
                }
            }, String.valueOf(i)).start();
        }
    }
}
