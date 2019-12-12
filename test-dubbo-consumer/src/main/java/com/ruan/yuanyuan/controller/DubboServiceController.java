package com.ruan.yuanyuan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ruan.yuanyuan.dubbo.service.TestDubboService;
import com.ruan.yuanyuan.entity.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultObject getString(){
        System.out.println(testDubboService.dubboService());
        return new ResultObject();
    }
}
