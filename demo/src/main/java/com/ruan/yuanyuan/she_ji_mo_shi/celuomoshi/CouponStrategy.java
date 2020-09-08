package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

/**
 * @ClassName CouponStrategy
 * @Author ruanyuanyuan
 * @Date 2020/9/8-12:55
 * @Version 1.0
 * @Description TODO 优惠券策略模式
 **/
public class CouponStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        System.out.println("领取优惠券");
    }
}
