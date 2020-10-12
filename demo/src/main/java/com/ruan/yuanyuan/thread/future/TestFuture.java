package com.ruan.yuanyuan.thread.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @ClassName TestFuture
 * @Author ruanyuanyuan
 * @Date 2020/8/17-15:36
 * @Version 1.0
 * @Description TODO 测试Future
 **/
public class TestFuture {

    public static void main(String[] args) {
        TestFuture testFuture = new TestFuture();
        System.out.println(testFuture.getPrice());

    }

    public Set<Integer> getPrice(){
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        try {
            /**
             * @Author: ruanyuanyuan
             * @Date: 2020/8/17 16:15
             * @Description: 三个任务，将其组合到一起，最后将结果进行合并，如果其中有一个任务等待超时则将其抛弃
             **/
            CompletableFuture task1 = CompletableFuture.runAsync(new PriceTask(123,prices));
            CompletableFuture task2 = CompletableFuture.runAsync(new PriceTask(456,prices));
            CompletableFuture task3 = CompletableFuture.runAsync(new PriceTask(789,prices));
            CompletableFuture allTasks = CompletableFuture.allOf(task1,task2,task3);
            allTasks.get(3,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return prices;
    }

}

class PriceTask implements Runnable {

    private int productId;
    private Set<Integer> prices;

    public PriceTask() {
    }

    public PriceTask(int productId, Set<Integer> prices) {
        this.productId = productId;
        this.prices = prices;
    }

    @Override
    public void run(){
        int price =0;
        try {
            /**
             * @Author: ruanyuanyuan
             * @Date: 2020/8/17 16:17
             * @Description: 模拟每个任务执行的等待时间
             **/
            Thread.sleep((long)(Math.random() * 4000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        price = (int)(Math.random() * 4000);
        prices.add(price);
    }
}
