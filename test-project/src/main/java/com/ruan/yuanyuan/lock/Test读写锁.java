package com.ruan.yuanyuan.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁：指该锁一次只能被一个线程锁持有，对ReentrantLock与Synchronized都是独占锁
 * 共享锁：指该锁可以被多个线程锁持有，对ReentrantReadWriteLock读写锁来说，它的读锁时共享锁，写锁时独占锁
 */
public class Test读写锁 {

    /**
     * 缓存map
     */
    private static volatile Map<String, Object> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 模拟写缓存时加写锁
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "#######正在写入##########key:\t" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "#######写入完成##########");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 模拟写缓存时加读锁
     *
     * @param key
     */
    public void get(String key) {

        Lock lock = readWriteLock.readLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "########正在读取########");
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "########正在读取########key:\t" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {

        /**
         * 10个线程写数据
         */
        for (int i = 0; i < 10; i++) {
            final String k = String.valueOf(i);
            new Thread(() -> {
                new Test读写锁().put(k, k + "==");
            }, String.valueOf(i)).start();
        }


        /**
         * 20个线程读数据
         */
        for (int i = 0; i < 10; i++) {
            final String k = String.valueOf(i);
            new Thread(() -> {
                new Test读写锁().get(k);
            }, String.valueOf(i)).start();
        }

    }
}
