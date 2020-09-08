package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PromotionStrategyFactory
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:07
 * @Version 1.0
 * @Description TODO 促销活动策略模式工厂类
 **/
public class PromotionStrategyFactory {

    private static Map<String,PromotionStrategy> map = new HashMap<>();

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/9/8 13:23
     * @Description: 通过静态模块初始化
     **/
    static {
        map.put(PromotionStrategyEnum.COUPON.name(),new CouponStrategy());
        map.put(PromotionStrategyEnum.CASH.name(),new CashBackStrategy());
    }

    private PromotionStrategyFactory() {

    }

    public static PromotionStrategy getPromotionStrategy(PromotionStrategyEnum promotionStrategyEnum){
        PromotionStrategy promotionStrategy = map.get(promotionStrategyEnum.name());
        /**
         * @Author: ruanyuanyuan
         * @Date: 2020/9/8 13:15
         * @Description: 也就是说如果没有获取到对于的促销策略就返回没有优惠活动策略否则返回对于的优惠策略
         * @param promotionKey:
         * @return: com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.PromotionStrategy
         **/
        return promotionStrategy == null? new EmptyStrategy():promotionStrategy;
    }


}
