package com.ruan.yuanyuan.feign;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.feign.callback.OrderServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: OrderServiceFeign
 * @author: ruanyuanyuan
 * @date: 2019/12/26 15:33
 * @version: 1.0
 * @description: 订单服务Feign
 **/
@FeignClient(value = "order-service",fallback = OrderServiceFallBack.class)
public interface OrderServiceFeign {

    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @return ResultObject
     */
    @RequestMapping(value = "/order/update",method = RequestMethod.GET)
    ResultObject update(@RequestParam("orderId") String orderId);

}
