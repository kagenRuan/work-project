package com.ruan.yuanyuan.生产者与消费者;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryy
 * @Title: ${A线程执行完B线程执行然后C线程执行，每个线程执行10次，每一次打印5次}
 * @Description: ${使用Lock Condition 唤醒指定的线程}
 * @date 2018/12/119:52
 */
public class Production_Consumer1 {

    volatile int num = 1;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    private void print5() {
        lock.lock();
        try {
            while (num != 1) {
                condition.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 2;
            //用于唤醒B其他线程
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print10() {
        lock.lock();
        try {
            while (num != 2) {
                condition1.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 3;
            //用于唤醒C其他线程
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print15() {
        lock.lock();
        try {
            while (num != 3) {
                condition2.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 1;
            //用于唤醒A其他线程
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Production_Consumer1 production_consumer1 = new Production_Consumer1();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                production_consumer1.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                production_consumer1.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                production_consumer1.print15();
            }
        }, "C").start();

    }

}
