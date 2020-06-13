package com.ruan.yuanyuan.mock;

import com.ruan.yuanyuan.service.TestDubboService;

/**
 * @ClassName: TestDubboServiceMock
 * @author: ruanyuanyuan
 * @date: 2020/6/2 15:57
 * @version: 1.0
 * @description: 用于TestDubboService服务降级
 **/
public class TestDubboServiceMock implements TestDubboService {

    @Override
    public void dubboService() {
        System.out.println("服务降级。。。。。");
    }
}
