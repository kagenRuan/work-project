package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

/**
 * @ClassName PromotionActivity
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:00
 * @Version 1.0
 * @Description TODO 活动
 **/
public class PromotionActivity {

    private PromotionStrategy promotionStrategy;

    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute(){
        promotionStrategy.doPromotion();
    }
}
