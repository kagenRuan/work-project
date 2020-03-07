package com.ruan.yuanyuan.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.remoting.transport.dispatcher.all.AllDispatcher;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.alibaba.dubbo.remoting.transport.netty.NettyServer;
import com.ruan.yuanyuan.service.TestDubboService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DubboServiceController
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:11
 * @version: 1.0
 * @description:
 **/
@RestController
public class DubboServiceController {

    @Reference(version = "1.0.0")
    TestDubboService testDubboService;


    @RequestMapping("/getString")
    public String getString() {
        testDubboService.dubboService();
        return "success";
    }
}
