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
 * @date: 2019/12/23 17:09
 * @version: 1.0
 * @description:
 **/
@Component
public class OrderServiceFallBack implements OrderServiceFeign {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceFeign.class);

    @Override
    public ResultObject test() {
        logger.error("<<<<<<OrderServiceFallBack#test>>>>>>>>>订单服务调用失败");
        return new ResultObject(ResultObjectEnum.FAIL.getCode(),ResultObjectEnum.FAIL.getMessage());
    }
}
