package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.CouponEntity;

/**
 * 优惠券服务接口
 */
public interface ICouponService extends IService<CouponEntity> {

    boolean  add(CouponEntity couponEntity);
}
