package com.ruan.yuayuan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ruan.yuanyuan.dubbo.service.TestDubboService;
/**
 * @ClassName: TestDubboServiceImpl
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:09
 * @version: 1.0
 * @description:
 **/
@Service(version = "1.0.0")
public class TestDubboServiceImpl implements TestDubboService {

    @Override
    public String dubboService() {
        return "hello dubbo";
    }
}
