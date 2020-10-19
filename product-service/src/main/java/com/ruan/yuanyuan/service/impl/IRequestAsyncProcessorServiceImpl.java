package com.ruan.yuanyuan.service.impl;

import com.ruan.yuanyuan.request.Request;
import com.ruan.yuanyuan.service.IRequestAsyncProcessorService;
import com.ruan.yuanyuan.thread.RequestProcessorThreadPool;
import org.springframework.stereotype.Service;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName IRequestAsyncProcessorServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/10/15-15:28
 * @Version 1.0
 * @Description TODO 请求异步处理实现类，主要负责请求的路由
 **/
@Service
public class IRequestAsyncProcessorServiceImpl implements IRequestAsyncProcessorService {

    @Override
    public void processor(Request request) {
        try {
            //将商品的请求路由到ArrayBlockingQueue队列中
            ArrayBlockingQueue routeQueue = getRouteQueue(request.getProductId());
            //将请求添加到队列中
            routeQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:32
     * @Description: 获取到路由到的队列
     * @param productId: 商品ID
     **/
    private ArrayBlockingQueue getRouteQueue(String productId){
        int queueSize = RequestProcessorThreadPool.getQueueSize();
        int h;
        int hash = (productId == null) ? 0 : (h = productId.hashCode()) ^ (h >>> 16);
        //将指定的Product路由到指定的队列中
        int index = (queueSize-1) & hash;
        System.out.println("请求路由-》商品ID="+productId+"，队列index="+index);
        ArrayBlockingQueue blockingQueue = RequestProcessorThreadPool.getQueue(index);
        return blockingQueue;

    }
}
