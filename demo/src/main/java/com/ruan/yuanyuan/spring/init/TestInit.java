package com.ruan.yuanyuan.spring.init;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName TestInit
 * @Author ruanyuanyuan
 * @Date 2020/9/2-14:39
 * @Version 1.0
 * @Description TODO  Bean初始化@PostConstruct
 **/
@Component
public class TestInit {

    @PostConstruct
    public void init(){
        System.out.println("Bean使用【@PostConstruct】注解初始化");
    }

}
