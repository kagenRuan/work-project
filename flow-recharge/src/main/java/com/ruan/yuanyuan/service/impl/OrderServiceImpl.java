package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.entity.Buyer;
import com.ruan.yuanyuan.entity.OrderEntity;
import com.ruan.yuanyuan.mapper.order.OrderMapper;
import com.ruan.yuanyuan.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * 订单服务接口
 */
@Service
public class OrderServiceImpl  extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

    /**
     * 添加订单
     * @param orderEntity
     */
    @Override
    public void add(OrderEntity orderEntity) {
        baseMapper.insert(orderEntity);
    }
}
