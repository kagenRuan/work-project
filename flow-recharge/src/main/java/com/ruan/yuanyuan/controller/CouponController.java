package com.ruan.yuanyuan.controller;


import com.ruan.yuanyuan.entity.CouponEntity;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private ICouponService couponService;

    @RequestMapping("/add")
    public ResultObject add(@RequestBody CouponEntity couponEntity){
       couponService.add(couponEntity);
       return new ResultObject();
    }
}
