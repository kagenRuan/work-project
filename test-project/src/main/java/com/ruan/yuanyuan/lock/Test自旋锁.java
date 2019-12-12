package com.ruan.yuanyuan.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 在获取锁的时候不会阻塞，而是通过不断的循环方式获取锁，好处是减少上下文的切换，坏处是增加了spu的小号
 */
public class Test自旋锁 {


    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 加锁
     */
    public void lock() {
        Thread thread = null;
        do {
            /**
             * 获取到当前线程
             */
            thread = Thread.currentThread();
            System.out.println(thread.getName() + "开始加锁");
            /**
             * 获取到当前线程和atomicReference中主内存中的Thread做比较，如果是空则把当前线程放入到里面
             * 这里为什么用null比较，是因为引用类型在实例化时值为null
             */
        } while (!atomicReference.compareAndSet(null, thread));
    }

    /**
     * 解锁
     * 如果atomicReference中主内存中的Thread与当前线程比较相等则把主内存的值修改为null
     */
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "开始解锁");

    }


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Test自旋锁 test自旋锁 = new Test自旋锁();
                test自旋锁.lock();
                test自旋锁.unlock();
            }, String.valueOf(i)).start();
        }

    }
}
