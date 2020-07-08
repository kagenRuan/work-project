package com.ruan.yuanyuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ruan.yuanyuan.dao")
public class ShiroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroServiceApplication.class, args);
    }

}
