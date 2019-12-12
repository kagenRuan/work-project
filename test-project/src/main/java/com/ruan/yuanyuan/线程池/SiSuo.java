package com.ruan.yuanyuan.线程池;

import java.util.concurrent.TimeUnit;

/**
 * @author ryy
 * @Title: ${死锁}
 * @Description: ${死锁的原因以及分析}
 * 死锁的原因：多个线程互相争抢资源造成互相等待
 * windows下使用命令 jsp -l 查看死锁进程
 * jstack  进程号 分析死锁
 */
public class SiSuo implements Runnable {

    private String lockA;
    private String lockB;

    public SiSuo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new SiSuo(lockA, lockB), "AAA").start();
        new Thread(new SiSuo(lockB, lockA), "BBB").start();
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有锁" + lockA + " 尝试获取锁" + lockB);

            try {
                TimeUnit.SECONDS.sleep(2l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有锁" + lockB + "尝试获取锁" + lockA);
            }
        }
    }
}
