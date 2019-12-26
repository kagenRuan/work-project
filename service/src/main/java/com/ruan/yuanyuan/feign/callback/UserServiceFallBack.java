package com.ruan.yuanyuan.feign.callback;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.ResultObjectEnum;
import com.ruan.yuanyuan.feign.UserServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ClassName: UserServiceFallBack
 * @author: ruanyuanyuan
 * @date: 2019/12/26 15:23
 * @version: 1.0
 * @description: 用户服务降级
 **/
@Component
public class UserServiceFallBack implements UserServiceFeign {

    private static Logger logger = LoggerFactory.getLogger(UserServiceFallBack.class);

    @Override
    public ResultObject updateMoneyById(String userId, BigDecimal money) {
        logger.error("<<<<<<UserServiceFallBack#updateMoneyById>>>>>>>>>用户服务调用失败，接口已降级");
        return new ResultObject(ResultObjectEnum.SYSTEM_HYSTRIX.getCode(),ResultObjectEnum.SYSTEM_HYSTRIX.getMessage());
    }

    @Override
    public ResultObject test() {
        logger.error("<<<<<<UserServiceFallBack#test>>>>>>>>>用户服务调用失败，接口已降级");
        return new ResultObject(ResultObjectEnum.SYSTEM_HYSTRIX.getCode(),ResultObjectEnum.SYSTEM_HYSTRIX.getMessage());
    }
}
