package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.feign.OrderServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProductController
 * @author: ruanyuanyuan
 * @date: 2019/12/23 16:56
 * @version: 1.0
 * @description:商品控制器
 **/
@RestController
public class ProductController {

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping("/getProductInfo")
    public ResultObject getProductInfo(){
       return orderServiceFeign.test();
    }
}
