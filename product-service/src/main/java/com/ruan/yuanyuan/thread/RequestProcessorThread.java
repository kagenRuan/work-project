package com.ruan.yuanyuan.thread;

import com.ruan.yuanyuan.request.ProductInventoryCacheReLoadRequest;
import com.ruan.yuanyuan.request.ProductInventoryUpdateRequest;
import com.ruan.yuanyuan.request.Request;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @ClassName WorkThread
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:36
 * @Version 1.0
 * @Description TODO
 **/
public class RequestProcessorThread implements Callable<Boolean> {

    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
       try {
           Map<String,Boolean> flagMap = RequestProcessorThreadPool.getFlagMap();
           while (true){
               //从队列中获取请求
               Request request = queue.take();
               if(!request.isForceRefresh()){
                   //TODO 读请求去重
                   //TODO 如果请求时写请求，将其保存到map中设置为true
                   if(request instanceof ProductInventoryUpdateRequest){
                       flagMap.put(request.getProductId(),true);
                       System.out.println("写请求：商品ID="+request.getProductId());
                   }else if(request instanceof ProductInventoryCacheReLoadRequest){

                       Boolean flag = flagMap.get(request.getProductId());
                       //TODO 如果之前没有写请求，也就是读请求发生在写请求之前，那么就把标识修改为false,然后直接返回，
                       // 此时会在ProductInventoryController层进行while循环，然后去数据库查询数据并set到缓存中
                       if(null == flag){
                           flagMap.put(request.getProductId(),false);
                           System.out.println("第一次请求就是读请求：商品ID="+request.getProductId());
                       }
                       //TODO 如果是读请求，则判断之前是否已经有写请求，如果flagMap已经有写请求并且标识为true,那就将其修改为false,同时将读请求加入到队列中
                       if(flag != null && flag){
                           flagMap.put(request.getProductId(),false);
                           System.out.println("写请求：商品ID="+request.getProductId());
                       }
                       //TODO 如果是读请求，flagMap中不为空，并且标识为false,那么说明当前商品之前已经有一个写请求和一个读请求了，
                       // 此时则不需要再将读请求路由到队列中
                       if(flag != null && !flag){
                           System.out.println("第N次读请求：商品ID="+request.getProductId());
                           return true;
                       }
                   }
               }
               //处理请求
               request.process();
           }
       }catch (Exception e){
            e.printStackTrace();
       }
        return true;
    }
}
