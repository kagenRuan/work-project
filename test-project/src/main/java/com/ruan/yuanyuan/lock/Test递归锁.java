package com.ruan.yuanyuan.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 递归所也可以称为是可重入锁
 * 意在一个方法调用另外一个方法，而这两个方法上面都有加锁，那么这时候调用时，他们获取的锁就是同一把锁
 */
public class Test递归锁 {

    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "#####发送短信");
        sendMq();
    }

    public synchronized void sendMq() {
        System.out.println(Thread.currentThread().getName() + "#####发送消息");
    }


    Lock lock = new ReentrantLock();

    public void sendSmsLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "#####发送短信");
            sendMqLock();
        } finally {
            lock.unlock();
        }
    }

    public void sendMqLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "#####发送消息");
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                new Test递归锁().sendSmsLock();
            }, String.valueOf(i)).start();
        }
    }
}
