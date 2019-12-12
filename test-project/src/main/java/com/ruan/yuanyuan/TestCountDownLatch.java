package com.ruan.yuanyuan;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch用于当所有的线程都执行完后，统一做下一步流程;
 * 拿到初始值进行减法，当最终为0时执行下一步流程；
 */
public class TestCountDownLatch {

    public static void main(String[] args) {

        TestCountDownLatch testCountDownLatch = new TestCountDownLatch();
        testCountDownLatch.TestCountDownLatch2();
    }

    public void TestCountDownLatch2() {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        try {
            for (int i = 1; i < 7; i++) {
                Integer index = i;
                new Thread(() -> {
                    System.out.println("#######" + CityEnum.getNameByNum(index).getName() + "被灭#########");
                    countDownLatch.countDown();
                }).start();
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("城市都已被灭");
    }


    public void TestCountDownLatch1() {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        try {
            for (int i = 0; i < 6; i++) {
                String index = String.valueOf(i);
                new Thread(() -> {
                    System.out.println("########执行" + index + "次#########");
                    countDownLatch.countDown();
                }).start();
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //以上六个线程都已执行完后才这行这一步
        System.out.println("已经执行6次");
    }
}
