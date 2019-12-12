package com.ruan.yuanyuan.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.ruan.yuanyuan.entity.Order;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @ClassName: OrderHystrixCommandListInfo
 * @author: ruanyuanyuan
 * @date: 2019/12/6 23:00
 * @version: 1.0
 * @description: 订单Hystrix降级自定义类返回多条数据
 **/
public class OrderHystrixCommandListInfo extends HystrixObservableCommand<Order> {

    //订单ID
    private String[] orderIds;

    public OrderHystrixCommandListInfo(String[] orderIds) {
        super(HystrixCommandGroupKey.Factory.asKey("OrderHystrixCommandListInfoGroup"));
        this.orderIds = orderIds;
    }

    @Override
    protected Observable construct() {
        return Observable.create(new Observable.OnSubscribe<Order>() {
            @Override
            public void call(Subscriber<? super Order> subscriber) {
                try {
                    /**
                     * 执行调用服务
                     */
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
