package com.ruan.yuanyuan.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁
public class TestReentrantReadWriteLock {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        readLock.lock();
        writeLock.lock();
        readLock.unlock();
        writeLock.unlock();
    }
}
