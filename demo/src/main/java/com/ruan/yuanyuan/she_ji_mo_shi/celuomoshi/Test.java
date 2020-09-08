package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

/**
 * @ClassName Test
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:01
 * @Version 1.0
 * @Description TODO 测试类
 **/
public class Test {

    public static void main(String[] args) {
        PromotionStrategy promotionStrategy = PromotionStrategyFactory.getPromotionStrategy(PromotionStrategyEnum.COUPON);
        PromotionActivity promotionActivity = new PromotionActivity(promotionStrategy);
        promotionActivity.execute();
    }
}
