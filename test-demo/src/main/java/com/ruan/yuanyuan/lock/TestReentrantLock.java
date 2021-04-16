package com.ruan.yuanyuan.lock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

//        new Thread(()->{
//            reentrantLock.lock();//加锁
//            try {
//                Thread.sleep(90000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            reentrantLock.unlock();
//            System.out.println("释放锁");
//        },"A").start();
//
        new Thread(()->{
            System.out.println("ThreadB加锁");
            reentrantLock.lock();//加锁
            try {
                System.out.println("ThreadB阻塞");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadB释放锁");
            reentrantLock.unlock();
        },"B").start();

        Thread.sleep(10000);
        new Thread(()->{
            System.out.println("ThreadA加锁");
            reentrantLock.lock();//加锁
            System.out.println("ThreadA唤醒线程B");
            condition.signal();
            reentrantLock.unlock();
            System.out.println("ThreadA释放锁");
        },"C").start();
    }
}
