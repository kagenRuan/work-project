package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.OrderEntity;

/**
 * 订单服务接口
 */
public interface IOrderService extends IService<OrderEntity> {

    void  add(OrderEntity orderEntity);
}
