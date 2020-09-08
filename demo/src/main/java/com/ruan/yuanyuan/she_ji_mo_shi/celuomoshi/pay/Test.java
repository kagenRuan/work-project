package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.payenum.PayTypeEnum;

import java.math.BigDecimal;

/**
 * @ClassName Test
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:45
 * @Version 1.0
 * @Description TODO
 **/
public class Test {

    public static void main(String[] args) {
        Order order = new Order("1","33333",new BigDecimal(400));
        ResultMessage resultMessage =  order.pay(PayTypeEnum.WECHAT_PAY);
        System.out.println(resultMessage);
    }
}
