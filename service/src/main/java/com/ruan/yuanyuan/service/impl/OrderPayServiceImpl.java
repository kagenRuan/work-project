package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.OrderPayMapper;
import com.ruan.yuanyuan.entity.Order;
import com.ruan.yuanyuan.entity.OrderPay;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IOrderPayService;
import com.ruan.yuanyuan.service.IOrderService;
import com.ruan.yuanyuan.util.PaySnUtile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 10:06
 * version:
 * Description:
 */
@Service
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

    @Autowired
    private IOrderService orderService;

    /**
     * 创建支付订单
     * @param orderIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderPay create(List<String> orderIds) {
        BusinessAssert.isFalse(ObjectUtils.isEmpty(orderIds), ExceptionUtil.OrderExceptionEnum.ORDER_ID_NOT_NULL);
        Collection<Order> orderList = orderService.listByIds(orderIds);
        BusinessAssert.isFalse(ObjectUtils.isEmpty(orderList),ExceptionUtil.OrderExceptionEnum.ORDER_NOT_EXITS);
        //支付单号
        String paySn = PaySnUtile.getPaySn();
        OrderPay orderPay = new OrderPay();
        orderPay.setPaySn(paySn);
        BigDecimal amount = BigDecimal.ZERO;
        for(Order order:orderList){
            amount = amount.add(order.getAmount());
            orderPay.setAmount(amount);
            order.setPaySn(paySn);
            order.setPayStatus("1");//待支付
        }
        orderPay.setPayType("ZFB");
        orderPay.setStatus("1");//待支付
        boolean saveOrderPay = super.save(orderPay);
        BusinessAssert.isTrue(saveOrderPay,ExceptionUtil.OrderPayExceptionEnum.ORDER_PAY_CREATE_FAIL);
        boolean updateOrder = orderService.updateBatchById(orderList);
        BusinessAssert.isTrue(updateOrder,ExceptionUtil.OrderExceptionEnum.ORDER_UPDATE_FAIL);
        return orderPay;
    }
}
