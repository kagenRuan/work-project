package com.ruan.yuanyuan.生产者与消费者;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryy
 * @Title: ${生产者与消费者使用阻塞队列实现}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/12/119:52
 */
public class Production_ConsumerBlockQueue {
    //用于表示是否开启生产+消息
    private volatile boolean FLAG = true;

    private BlockingQueue<String> blockingQueue;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public Production_ConsumerBlockQueue(BlockingQueue blockingQueue){
        this.blockingQueue=blockingQueue;
    }

    public  void changState(){
        this.FLAG=false;
        System.out.println(blockingQueue.size());
    }
    //生产者
    public void production(){
        String data = null;
        boolean result;
        while (FLAG){
            try {
                data = atomicInteger.incrementAndGet()+"";
                result = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
                if(result){
                    System.out.println(Thread.currentThread().getName()+"\t 插入队列数据"+data+"成功");
                }else{
                    System.out.println(Thread.currentThread().getName()+"\t 插入队列数据"+data+"失败");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"\t 停止生产  ");
    }

    //消费者
    public void consumer(){
        String data = null;
        try{
            while (FLAG) {
                data = blockingQueue.poll(2L,TimeUnit.SECONDS);
                if(null == data || data.equalsIgnoreCase("")){
                    FLAG = false;
                    return;
                }
                System.out.println(Thread.currentThread().getName()+"\t 从队列中获取到值："+data);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Production_ConsumerBlockQueue production_consumerBlockQueue = new Production_ConsumerBlockQueue(new ArrayBlockingQueue(5));
        new Thread(()->{production_consumerBlockQueue.production();},"A").start();
        new Thread(()->{production_consumerBlockQueue.consumer();},"B").start();

//        try {
//            TimeUnit.SECONDS.sleep(5);
//            production_consumerBlockQueue.changState();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
