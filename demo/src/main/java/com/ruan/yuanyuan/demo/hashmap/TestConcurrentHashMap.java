package com.ruan.yuanyuan.demo.hashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: TestConcurrentHashMap
 * @author: ruanyuanyuan
 * @date: 2020/7/27 16:42
 * @version: 1.0
 * @description: 分析ConcurrentHashMap
 **/
public class TestConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        for(int i=0;i<20;i++){
            int finalI = i;
            new Thread(()->{
                hashMap.put(Thread.currentThread().getName(), finalI +"");
            },i+"").start();
        }
    }
}
