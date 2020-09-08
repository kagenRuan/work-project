package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.paytype;

import java.math.BigDecimal;

/**
 * @ClassName UnionPay
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:43
 * @Version 1.0
 * @Description TODO
 **/
public class UnionPay extends Payment {
    @Override
    public String getName() {
        return "银联支付";
    }

    @Override
    public BigDecimal getAmount(String uid) {
        return new BigDecimal(100);
    }
}
