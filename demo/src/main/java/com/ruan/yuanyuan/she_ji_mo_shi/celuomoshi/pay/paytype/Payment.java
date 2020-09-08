package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.paytype;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.pay.ResultMessage;

import java.math.BigDecimal;

/**
 * @ClassName Payment
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:35
 * @Version 1.0
 * @Description TODO
 **/
public abstract class Payment {

  public abstract String getName();
  public abstract BigDecimal getAmount(String uid);

  public  ResultMessage doPay(String uid, BigDecimal amount){
        if(getAmount(uid).compareTo(amount) < 0){
            return new ResultMessage("支付失败","500",amount);
        }
      return new ResultMessage("支付成功","200",amount);
  }

}
