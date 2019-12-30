package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.dto.ProductDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.hystrix.ProductHystrixCommand;
import com.ruan.yuanyuan.service.IOrderService;
import com.ruan.yuanyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 12:00
 * version:
 * Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResultObject createOrder(@RequestBody List<ProductDto> productDtoList) {
        orderService.createOrder(productDtoList);
        return new ResultObject();
    }

    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.GET)
    public ResultObject getOrderInfo() {
        ProductHystrixCommand productHystrixCommand = new ProductHystrixCommand("1");
        ResultObject resultObject = productHystrixCommand.execute();
        return resultObject;
    }

    /**
     * 修改订单状态
     * @param orderId 订单id
     * @return ResultObject
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ResultObject update(String orderId){
       return orderService.updateOrder(orderId,"");
    }

}
