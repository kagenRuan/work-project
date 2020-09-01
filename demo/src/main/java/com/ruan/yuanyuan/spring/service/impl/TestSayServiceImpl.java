package com.ruan.yuanyuan.spring.service.impl;

import com.ruan.yuanyuan.spring.service.TestHellService;
import com.ruan.yuanyuan.spring.service.TestSayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestSayServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:12
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class TestSayServiceImpl implements TestSayService {

    @Autowired
    private TestHellService testHellService;

    @Override
    public void sayHello() {
        System.out.println("say hello");
    }
}
