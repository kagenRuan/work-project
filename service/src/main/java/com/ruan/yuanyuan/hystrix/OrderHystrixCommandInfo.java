package com.ruan.yuanyuan.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.ruan.yuanyuan.entity.Order;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @ClassName: OrderHystrixCommandInfo
 * @author: ruanyuanyuan
 * @date: 2019/12/6 22:52
 * @version: 1.0
 * @description: 订单Hystrix降级自定义类, 这个只能用于查询一条数据
 **/
public class OrderHystrixCommandInfo extends HystrixCommand<Order> {

    private Order order;

    public OrderHystrixCommandInfo(Order order) {
        super(HystrixCommandGroupKey.Factory.asKey("OrderHystrixCommandGroup"));
        this.order = order;

    }

    @Override
    protected Order run() throws Exception {
        /**
         * 这里可以进行服务调用并返回结果
         */
        return null;
    }

    /**
     * 请求超时调用
     *
     * @return
     */
    @Override
    protected Order getFallback() {
        return super.getFallback();
    }
}
