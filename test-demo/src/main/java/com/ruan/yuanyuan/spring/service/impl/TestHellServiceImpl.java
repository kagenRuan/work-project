package com.ruan.yuanyuan.spring.service.impl;

import com.ruan.yuanyuan.spring.service.TestHellService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName TestHellServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/8/29-14:45
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class TestHellServiceImpl implements TestHellService {

//    @Autowired
//    private TestSayService testSayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void hello() {
        System.out.println("test hello");
    }
}
