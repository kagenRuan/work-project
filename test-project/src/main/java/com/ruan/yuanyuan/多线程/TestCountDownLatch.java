package com.ruan.yuanyuan.多线程;

import com.ruan.yuanyuan.CityEnum;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch用于当所有的线程都执行完后，统一做下一步流程;
 * 拿到初始值进行减法，当最终为0时执行下一步流程；
 */
public class TestCountDownLatch {

    static List<String> list = new Vector<>();
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) {
        //例子1
        TestCountDownLatch testCountDownLatch = new TestCountDownLatch();
        testCountDownLatch.TestCountDownLatch2();

        //例子2
        list.add("1");
        list.add("2");
        list.add("3");
        testCountDownLatch.TestCountDownLatchA(list);
        testCountDownLatch.TestCountDownLatchB(list);
        testCountDownLatch.TestCountDownLatchC(list);
        if (countDownLatch.getCount() == 0) {
            testCountDownLatch.print();
        }
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

    public void TestCountDownLatchA(List<String> list) {
        new Thread(() -> {
            try {
                if (list.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + "删除数组中索引值为0的数据：" + list.get(0));
                    list.remove(0);
                    countDownLatch.countDown();
                }
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
    }

    public void TestCountDownLatchB(List<String> list) {
        new Thread(() -> {
            try {
                if (list.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + "删除数组中索引值为0的数据：" + list.get(0));
                    list.remove(0);
                    countDownLatch.countDown();
                }
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    public void TestCountDownLatchC(List<String> list) {
        new Thread(() -> {
            try {
                if (list.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + "删除数组中索引值为0的数据：" + list.get(0));
                    list.remove(0);
                    countDownLatch.countDown();
                }
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
    }


    public void print() {
        System.out.println("执行完了,最终数组的长度=" + list.size());
    }
}
