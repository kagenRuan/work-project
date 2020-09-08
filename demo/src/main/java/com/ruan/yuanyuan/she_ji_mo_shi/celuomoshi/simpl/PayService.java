package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.payenum.PayTypeEnum;

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
