package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

/**
 * @ClassName CashBackStrategy
 * @Author ruanyuanyuan
 * @Date 2020/9/8-12:57
 * @Version 1.0
 * @Description TODO
 **/
public class CashBackStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        System.out.println("返现优惠");
    }
}
