package com.ruan.yuanyuan.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS(compareAndSet)比较与交换理论
 */
public class CASTest {

    public static void main(String[] args) {
        /**
         * 举例：
         * 1.用主内存的初始值initialValue：5
         * 2.线程的期望值expect:5如果与主内存的值相等
         * 3.将主内存中的值修改为update:2019
         */
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndAdd(1);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "==" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "==" + atomicInteger.get());
    }
}
