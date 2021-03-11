package com.ruan.yuanyuan.controller;


import com.ruan.yuanyuan.entity.OrderEntity;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/add")
    public ResultObject add(@RequestBody OrderEntity orderEntity){
        orderService.add(orderEntity);
        return new ResultObject();
    }
}
