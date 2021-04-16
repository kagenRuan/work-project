package com.ruan.yuanyuan.feign;

import com.ruan.yuanyuan.entity.ResultObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: OrderServiceFeign
 * @author: ruanyuanyuan
 * @date: 2019/12/23 16:52
 * @version: 1.0
 * @description: 订单服务Feign调用
 **/
@FeignClient(name = "order-service",value = "order-service",fallback = OrderServiceFallBack.class) //value 指定服务名称
public interface OrderServiceFeign {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    ResultObject test();
}
