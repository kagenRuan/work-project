package com.ruan.yuanyuan.spring.config;

import com.ruan.yuanyuan.spring.entity.TestSay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * @ClassName TestConfig
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:10
 * @Version 1.0
 * @Description TODO
 **/
@Configuration("config")
@ComponentScan(basePackages = "com.ruan.yuanyuan.spring")
public class TestConfig {

    @Bean(value = "testSay1")
    public TestSay testSay(){
       return new TestSay();
   }

}
