package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.payenum.PayTypeEnum;
import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.paytype.Payment;

import java.math.BigDecimal;

/**
 * @ClassName Order
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:32
 * @Version 1.0
 * @Description TODO
 **/
public class Order implements PayService {

    private String uid;
    private String orderId;
    private BigDecimal amount;

    public Order(String uid, String orderId, BigDecimal amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/9/8 14:14
     * @Description: 使用策略模式封装支付
     * @param payTypeEnum: 支付类型
     * @return: com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.pay.ResultMessage
     **/
    @Override
    public ResultMessage pay(PayTypeEnum payTypeEnum) {
        Payment payment = PayStrategy.getPayment(payTypeEnum);
        System.out.println("支付方式："+payment.getName());
        ResultMessage resultMessage = payment.doPay(uid,amount);
        return resultMessage;
    }
}
