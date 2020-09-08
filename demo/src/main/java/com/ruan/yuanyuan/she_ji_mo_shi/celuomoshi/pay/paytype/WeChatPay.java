package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.paytype;

import java.math.BigDecimal;

/**
 * @ClassName WechatPay
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:42
 * @Version 1.0
 * @Description TODO
 **/
public class WeChatPay extends Payment {

    @Override
    public String getName() {
        return "微信支付";
    }

    @Override
    public BigDecimal getAmount(String uid) {
        return new BigDecimal(200);
    }
}
