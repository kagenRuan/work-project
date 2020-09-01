package com.ruan.yuanyuan.spring.controller;

import com.ruan.yuanyuan.spring.entity.TestSayFactoryBean;
import com.ruan.yuanyuan.spring.factoryBean.TestFactoryBean;
import com.ruan.yuanyuan.spring.service.TestSayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TestController
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:14
 * @Version 1.0
 * @Description TODO
 **/
@Controller
public class TestController {

    private String id;

    public TestController() {
    }

    public TestController(String id) {
        this.id = id;
    }

    @Autowired
    private TestFactoryBean testFactoryBean;

    @RequestMapping("/say")
    public void say() throws Exception {
        TestSayFactoryBean testSayFactoryBean = (TestSayFactoryBean) testFactoryBean.getObject();
        System.out.println(testSayFactoryBean.getId());
    }
}
