package com.ruan.yuanyuan.生产者与消费者;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryy
 * @Title: ${生产者与消费者}
 * @Description: ${生产者与消费者 举例对一个数字进行加一减一，也就是加一后马上加一}
 */
public class Production_Consumer {

    private int num = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    /**
     * 加一
     */
    public void increment(){
        //加锁
        lock.lock();
        try {
            /**
             * 如果num不为0时则需要等待不能对num进行加一
             */
            while ( 0 != num){
              condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            //唤醒其他线程
            condition.signalAll();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 减一
     */
    public void decrement(){
        lock.lock();
        try {
            /**
             * 当num为0时则对num加一
             */
            while ( 0 == num){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            condition.signalAll();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Production_Consumer production_consumer = new Production_Consumer();

        new Thread(()->{
            //每个线程执行5次
            for(int i=1;i<=5;i++){
                production_consumer.decrement();
            }
        },"B线程").start();


        new Thread(()->{
            //每个线程执行5次
            for(int i=1;i<=5;i++){
                production_consumer.increment();
            }
        },"A线程").start();


    }
}
