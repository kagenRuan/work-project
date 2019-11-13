package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.OrderPay;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 10:05
 * version:1.0
 * Description:订单支付服务
 */
public interface IOrderPayService extends IService<OrderPay> {

    /**
     * 创建支付单号
     * @param orderId
     * @return
     */
    OrderPay create(List<String> orderId);


}
