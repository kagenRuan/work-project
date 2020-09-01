package com.ruan.yuanyuan.spring.service.impl;

import com.ruan.yuanyuan.spring.service.TestHellService;
import com.ruan.yuanyuan.spring.service.TestSayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestHellServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/8/29-14:45
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class TestHellServiceImpl implements TestHellService {

    @Autowired
    private TestSayService testSayService;

    @Override
    public void hello() {
        System.out.println("test hello");
    }
}
