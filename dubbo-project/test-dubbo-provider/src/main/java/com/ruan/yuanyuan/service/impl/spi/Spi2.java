package com.ruan.yuanyuan.service.impl.spi;

import com.ruan.yuanyuan.service.TestDubboService;

/**
 * @ClassName: Spi2
 * @author: ruanyuanyuan
 * @date: 2019/12/12 16:12
 * @version: 1.0
 * @description:SPI扩展机制2
 **/
public class Spi2 implements TestDubboService {

    @Override
    public void dubboService() {
        System.out.println("SPI扩展机制2");
    }
}
