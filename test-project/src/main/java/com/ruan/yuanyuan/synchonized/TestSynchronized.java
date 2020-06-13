package com.ruan.yuanyuan.synchonized;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-11-03
 * @Time: 15:04
 * @version:1.0
 * @Description:
 */
public class TestSynchronized implements Runnable{

    private static int i=0;

    /**
     * TODO synchronized修饰方法方法,锁的范围属于【对象锁】
     */
    public  void test1() {
        i++;
        System.out.println("synchronized修饰方法方法");
    }

    /**
     * synchronized代码块
     */
    public void test2() {
        //TODO 如果使用this，锁范围属于【对象锁】
        synchronized (this) {
            i++;
            System.out.println("synchronized代码块");
        }
        //TODO 如果使用对象实例，锁范围属于【类锁】
//        synchronized (TestSynchronized.class) {
//            System.out.println("synchronized代码块 ");
//        }
    }

    /**
     * synchronized静态方法
     * TODO 如果使用静态方法，锁范围属于【类锁】
     */
    public synchronized static void test3() {
        i++;
        System.out.println("synchronized静态方法");
    }

    public static void main(String[] args) {
        TestSynchronized testSynchronized = new TestSynchronized();
        Thread t1= new Thread(testSynchronized);
        Thread t2= new Thread(testSynchronized);
        t1.start();
        t2.start();
        System.out.println("结果i="+testSynchronized.i);

    }

    @Override
    public synchronized void run() {
        i=i+1;
    }
}
