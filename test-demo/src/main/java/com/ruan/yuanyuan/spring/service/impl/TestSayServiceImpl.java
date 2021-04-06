package com.ruan.yuanyuan.spring.service.impl;

import com.ruan.yuanyuan.spring.service.TestSayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName TestSayServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:12
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class TestSayServiceImpl implements TestSayService {

//    @Autowired
//    private TestHellService testHellService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sayHello() {
        System.out.println("say hello");
    }
}
