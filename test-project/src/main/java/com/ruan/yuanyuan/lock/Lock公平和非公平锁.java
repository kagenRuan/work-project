package com.ruan.yuanyuan.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁：是指在等待队列中，按照先来先得到的顺序获取锁，意在FIFO（先进先出的等待队列）
 * 非公平锁：是指在公平锁的情况下，可以不用遵守先进先出，可以随时申请获取锁，这样保证了吞吐量上升不保证线程循序
 */
public class Lock公平和非公平锁 implements Runnable {

    private Lock lock ;

    public Lock公平和非公平锁(Lock lock) {
        this.lock = lock;
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock(false);
        new Thread(new Lock公平和非公平锁(lock),"A").start();
        new Thread(new Lock公平和非公平锁(lock),"B").start();
    }

    @Override
    public void run() {
    /**
     * 参数为true表示公平锁
     *      false表示为非公平锁
     */
    try{
        lock.lock();
    }finally {
        //lock.unlock();
    }
    }
}
