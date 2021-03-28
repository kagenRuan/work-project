package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.entity.CouponEntity;
import com.ruan.yuanyuan.entity.OrderEntity;
import com.ruan.yuanyuan.mapper.coupon.CouponMapper;
import com.ruan.yuanyuan.mapper.order.OrderMapper;
import com.ruan.yuanyuan.service.ICouponService;
import com.ruan.yuanyuan.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 优惠券服务接口
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponEntity> implements ICouponService {

    /**
     * 添加优惠券
     * @param couponEntity
     */
    @Transactional(transactionManager = "couponJtaTransactionManager",rollbackFor = Exception.class)
    @Override
    public boolean add(CouponEntity couponEntity){
        return super.save(couponEntity);
    }
}
