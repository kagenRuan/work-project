package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.entity.Buyer;
import com.ruan.yuanyuan.entity.CouponEntity;
import com.ruan.yuanyuan.entity.OrderEntity;
import com.ruan.yuanyuan.mapper.order.OrderMapper;
import com.ruan.yuanyuan.service.ICouponService;
import com.ruan.yuanyuan.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单服务接口
 */
@Service
public class OrderServiceImpl  extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

    @Autowired
    private ICouponService couponService;

    /**
     * 添加订单
     * @param orderEntity
     */
    @Transactional(transactionManager = "orderTransactionManager",rollbackFor = Exception.class)
    @Override
    public void add(OrderEntity orderEntity) {
        baseMapper.insert(orderEntity);
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setId(3l);
        couponEntity.setCouponAmount(new BigDecimal(10));
        couponEntity.setStartTime(new Date());
        couponEntity.setEndTime(new Date());
        couponEntity.setStatus(1);
        couponEntity.setUserAccountId(1l);
        couponService.add(couponEntity);

    }
}
