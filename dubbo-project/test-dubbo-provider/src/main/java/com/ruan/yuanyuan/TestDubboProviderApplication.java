package com.ruan.yuanyuan;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: TestDubboProviderAppliaction
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:06
 * @version: 1.0
 * @description:
 **/
@SpringBootApplication
@EnableDubbo
public class TestDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDubboProviderApplication.class, args);
    }
}
