package com.ruan.yuanyuan.thread;

import com.ruan.yuanyuan.request.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPool
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:25
 * @Version 1.0
 * @Description TODO 请求线程处理池
 **/
public class RequestProcessorThreadPool {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();
    private static Map<String,Boolean> flagMap = new ConcurrentHashMap<>();

    public RequestProcessorThreadPool() {
        for (int i = 0; i < 5; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            queues.add(queue);
            executorService.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 09:26
     * @Description: 通过静态内部类绝对安全的方式初始化单例
     **/
    private static class Singleton{

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        private static RequestProcessorThreadPool getInstance(){
            return instance;
        }

    }


    public static RequestProcessorThreadPool getInstance(){
        return Singleton.getInstance();
    }


    public static void init(){
        getInstance();
    }

    public static ArrayBlockingQueue getQueue(int index){
        return queues.get(index);
    }

    public static int getQueueSize(){
       return queues.size();
    }

    public static Map getFlagMap(){
        return flagMap;
    }
}
