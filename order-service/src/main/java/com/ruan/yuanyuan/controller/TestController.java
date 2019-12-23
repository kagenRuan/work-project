package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @author: ruanyuanyuan
 * @date: 2019/12/23 16:09
 * @version: 1.0
 * @description:
 **/
@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/test")
    public ResultObject test() {
        return new ResultObject(port);
    }
}
