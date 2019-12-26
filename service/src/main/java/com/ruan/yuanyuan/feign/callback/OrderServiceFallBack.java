package com.ruan.yuanyuan.feign.callback;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.ResultObjectEnum;
import com.ruan.yuanyuan.feign.OrderServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: OrderServiceFallBack
 * @author: ruanyuanyuan
 * @date: 2019/12/26 15:34
 * @version: 1.0
 * @description: 订单服务降级
 **/
@Component
public class OrderServiceFallBack implements OrderServiceFeign {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceFallBack.class);

    @Override
    public ResultObject update(String orderId) {
        logger.error("<<<<<<OrderServiceFallBack#update>>>>>>>>>订单服务调用失败，已进行降级");
        return new ResultObject(ResultObjectEnum.SYSTEM_HYSTRIX.getCode(),ResultObjectEnum.SYSTEM_HYSTRIX.getMessage());
    }
}
