package com.ruan.yuanyuan.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName TestArrayBlokingQueue
 * @Author ruanyuanyuan
 * @Date 2020/10/19-19:50
 * @Version 1.0
 * @Description TODO
 **/
public class TestArrayBlockingQueue {

   static ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(100);

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            add(i+"");
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //消费队列中的数据
        take();

    }


    private static void add(String param){
        try {
            blockingQueue.put(param);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void take(){
        Object result = null;
        try {
        while (true){
           result = blockingQueue.take();
           if(result.equals("2")){
               return;
           }
           System.out.println(result.toString()+",队列中的元素="+blockingQueue.size());
        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
