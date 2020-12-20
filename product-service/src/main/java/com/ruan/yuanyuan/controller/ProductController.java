package com.ruan.yuanyuan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.exception.BusinessException;
import com.ruan.yuanyuan.feign.OrderServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: ProductController
 * @author: ruanyuanyuan
 * @date: 2019/12/23 16:56
 * @version: 1.0
 * @description:商品控制器
 **/
@RestController
public class ProductController {

    @Resource
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping("/getProductInfo")
    public ResultObject getProductInfo(){
       return orderServiceFeign.test();
    }
}
