package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.paytype;

import java.math.BigDecimal;

/**
 * @ClassName AliPay
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:34
 * @Version 1.0
 * @Description TODO
 **/
public class AliPay extends Payment {

    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    public BigDecimal getAmount(String uid) {
        return new BigDecimal(500);
    }
}
