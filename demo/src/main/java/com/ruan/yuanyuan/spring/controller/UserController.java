package com.ruan.yuanyuan.spring.controller;

import com.ruan.yuanyuan.spring.factoryBean.TestFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:14
 * @Version 1.0
 * @Description TODO
 **/
@RestController
public class UserController {

    private String id;

    public UserController() {
    }

    public UserController(String id) {
        this.id = id;
    }

    @Autowired
    private TestFactoryBean testFactoryBean;

    @RequestMapping("/say")
    public void say(){
        System.out.println("say");
    }
}
