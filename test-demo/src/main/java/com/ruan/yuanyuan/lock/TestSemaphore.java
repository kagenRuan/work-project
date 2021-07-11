package com.ruan.yuanyuan.lock;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

public class TestSemaphore {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("","");

    }
}
