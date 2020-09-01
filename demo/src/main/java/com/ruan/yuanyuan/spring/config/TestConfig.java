package com.ruan.yuanyuan.spring.config;

import com.ruan.yuanyuan.spring.aop.TestAop;
import com.ruan.yuanyuan.spring.entity.TestSay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName TestConfig
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:10
 * @Version 1.0
 * @Description TODO
 **/
@Configuration
@ComponentScan(basePackages = "com.ruan.yuanyuan.spring")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestConfig {


    @Bean
    public TestSay testSay(){
       return new TestSay();
   }

}
