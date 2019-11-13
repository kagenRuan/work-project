package com.ruan.yuanyuan.spring.config;

import com.ruan.yuanyuan.annoation.RAutoWried;
import com.ruan.yuanyuan.spring.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-27
 * @Time: 15:10
 * @version:1.0
 * @Description: 测试配置文件
 */
@Configuration
@ComponentScan("com.ruan.yuanyuan")
public class RConfig {

    @RAutoWried
    private UserService userService;

    public void test(){
        userService.test();
    }




}
