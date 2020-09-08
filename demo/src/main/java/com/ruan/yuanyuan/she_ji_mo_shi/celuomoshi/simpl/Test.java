package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.payenum.PayTypeEnum;

import java.math.BigDecimal;

/**
 * @ClassName Test
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:45
 * @Version 1.0
 * @Description TODO 以多种支付方式实现策略模式
 **/
public class Test {

    public static void main(String[] args) {
        Order order = new Order("1","33333",new BigDecimal(400));
        ResultMessage resultMessage =  order.pay(PayTypeEnum.WECHAT_PAY);
        System.out.println(resultMessage);
    }
}
