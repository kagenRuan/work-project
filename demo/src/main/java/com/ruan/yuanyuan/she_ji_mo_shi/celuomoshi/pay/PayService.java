package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.payenum.PayTypeEnum;

/**
 * @ClassName PayService
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:33
 * @Version 1.0
 * @Description TODO 支付接口
 **/
@FunctionalInterface
public interface PayService {

    ResultMessage pay(PayTypeEnum payTypeEnum);

}
