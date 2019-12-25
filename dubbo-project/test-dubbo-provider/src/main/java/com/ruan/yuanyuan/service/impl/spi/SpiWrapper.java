package com.ruan.yuanyuan.service.impl.spi;

import com.ruan.yuanyuan.service.TestDubboService;

/**
 * @ClassName: SpiWrapper
 * @author: ruanyuanyuan
 * @date: 2019/12/12 16:38
 * @version: 1.0
 * @description:
 **/
public class SpiWrapper implements TestDubboService {

    private TestDubboService testDubboService;

    public SpiWrapper(TestDubboService testDubboService) {
        this.testDubboService = testDubboService;
    }

    @Override
    public void dubboService() {
        System.out.println("before");
        testDubboService.dubboService();
        System.out.println("after");
    }
}
