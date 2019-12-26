package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.dto.OrderPayDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.IOrderPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 12:00
 * version:
 * Description: 支付控制器
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderPayService orderPayService;

    /**
     * 支付成功后回调接口
     * @param payDto
     * @return ResultObject
     */
    @RequestMapping(value = "/payCallBack",method = RequestMethod.POST)
    public ResultObject payCallBack(@RequestBody OrderPayDto payDto){
        return  orderPayService.callBack(payDto);

    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResultObject test(){
        return orderPayService.test();
    }


}
