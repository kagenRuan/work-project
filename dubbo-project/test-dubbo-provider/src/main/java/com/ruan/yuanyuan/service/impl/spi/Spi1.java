package com.ruan.yuanyuan.service.impl.spi;

import com.ruan.yuanyuan.service.TestDubboService;

/**
 * @ClassName: Spi1
 * @author: ruanyuanyuan
 * @date: 2019/12/12 16:12
 * @version: 1.0
 * @description: SPI扩展机制1
 **/
public class Spi1 implements TestDubboService {

    @Override
    public void dubboService() {
        System.out.println("SPI扩展机制1");
    }
}
