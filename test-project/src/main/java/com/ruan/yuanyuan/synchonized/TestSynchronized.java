package com.ruan.yuanyuan.synchonized;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-11-03
 * @Time: 15:04
 * @version:1.0
 * @Description:
 */
public class TestSynchronized {

    public void  test1(){
        System.out.println("正常方法");
    }

    public void test2(){
        synchronized (this){
            System.out.println("方法块");
        }
    }

    public synchronized static void test3(){
        System.out.println("方法");
    }





}
