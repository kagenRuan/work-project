package com.ruan.yuanyuan.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockTest
 * @author: ruanyuanyuan
 * @date: 2020/7/25 23:33
 * @version: 1.0
 * @description: Lock
 **/
public class LockTest {

    private static int age = 0;

    public static void lock1(Lock lock){
        try{
            lock.lock();
            age +=1;
            System.out.println(age);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
