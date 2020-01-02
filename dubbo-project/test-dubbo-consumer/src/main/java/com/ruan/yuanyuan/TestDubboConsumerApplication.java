package com.ruan.yuanyuan;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: TestDubboConsumerApplication
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:00
 * @version: 1.0
 * @description: dubbo 服务消费者
 **/
@SpringBootApplication
@EnableDubbo
public class TestDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDubboConsumerApplication.class, args);
    }
}
