package com.ruan.yuanyuan;

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
public class TestDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDubboConsumerApplication.class,args);
    }
}
