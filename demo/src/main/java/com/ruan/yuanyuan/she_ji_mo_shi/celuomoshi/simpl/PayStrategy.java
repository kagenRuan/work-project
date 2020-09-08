package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl;

import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.payenum.PayTypeEnum;
import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.paytype.AliPay;
import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.paytype.Payment;
import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.paytype.UnionPay;
import com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl.paytype.WeChatPay;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PayStrategy
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:58
 * @Version 1.0
 * @Description TODO 支付策略模式
 **/
public class PayStrategy {

   private static Map<PayTypeEnum, Payment> map = new HashMap<>();

   static {
       map.put(PayTypeEnum.ALI_PAY,new AliPay());
       map.put(PayTypeEnum.WECHAT_PAY,new WeChatPay());
       map.put(PayTypeEnum.UNION_PAY,new UnionPay());
   }

   public static Payment getPayment(PayTypeEnum payTypeEnum){
        if(map.containsKey(payTypeEnum)){
            return map.get(payTypeEnum);
        }
        return map.get(PayTypeEnum.ALI_PAY);
   }
}
