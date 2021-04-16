package com.ruan.yuanyuan.feign;

import com.ruan.yuanyuan.entity.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
@FeignClient(value = "order-service",fallback = OrderServiceFeign.OrderServiceFallBack.class)
public interface OrderServiceFeign {

    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @param status 订单状态
     * @return ResultObject
     */
    @RequestMapping(value = "/order/update",method = RequestMethod.GET)
    ResultObject update(@RequestParam("orderId") String orderId,@RequestParam("status") String status);


    @Component
    static class OrderServiceFallBack implements OrderServiceFeign{

        private static Logger logger = LoggerFactory.getLogger(OrderServiceFallBack.class);

        @Override
        public ResultObject update(String orderId, String status) {
            logger.info("orderService#update fall back");
            return null;
        }
    }
}
