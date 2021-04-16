package com.ruan.yuanyuan.feign;

import com.ruan.yuanyuan.entity.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @ClassName: UserServiceFeign
 * @author: ruanyuanyuan
 * @date: 2019/12/26 15:22
 * @version: 1.0
 * @description: 用户服务Feign接口
 **/
@FeignClient(value = "user-service",fallback = UserServiceFeign.UserServiceFallBack.class) //value 指定服务名称
public interface UserServiceFeign {

    /**
     * 修改用户金额
     * @param userId 用户ID
     * @param money  金额
     * @return  ResultObject
     */
    @RequestMapping(value = "/user/updateMoneyById",method = RequestMethod.GET)
    ResultObject updateMoneyById(@RequestParam("userId") String userId, @RequestParam("money") BigDecimal money,@RequestParam String paySn);

    /**
     * 测试方法
     * @return
     */
    @RequestMapping(value = "/user/test",method = RequestMethod.GET)
    ResultObject test();


    @Component
    static  class UserServiceFallBack implements UserServiceFeign{

        private static Logger logger = LoggerFactory.getLogger(UserServiceFallBack.class);

        @Override
        public ResultObject updateMoneyById(String userId, BigDecimal money, String paySn) {
            logger.info("UserServiceFeign#updateMoneyById fall back");
            return null;
        }

        @Override
        public ResultObject test() {
            logger.info("UserServiceFeign#test fall back");
            return null;
        }
    }
}
