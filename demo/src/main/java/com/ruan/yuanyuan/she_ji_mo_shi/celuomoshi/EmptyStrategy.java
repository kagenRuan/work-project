package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

/**
 * @ClassName EnptyStrategy
 * @Author ruanyuanyuan
 * @Date 2020/9/8-11:09
 * @Version 1.0
 * @Description TODO 没有优惠的策略模式
 **/
public class EmptyStrategy implements PromotionStrategy{

    @Override
    public void doPromotion() {
        System.out.println("没有任何优惠");
    }
}
