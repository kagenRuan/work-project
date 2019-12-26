package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.OrderPay;
import com.ruan.yuanyuan.entity.ResultObject;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 10:05
 * version:1.0
 * Description:订单支付服务
 */
public interface IOrderPayService extends IService<OrderPay>,IPayCallBackService {

    /**
     * 创建支付单号
     *
     * @param orderId
     * @return
     */
    OrderPay create(List<String> orderId);

    /**
     * 测试方法
     * @return
     */
    ResultObject test();


}
