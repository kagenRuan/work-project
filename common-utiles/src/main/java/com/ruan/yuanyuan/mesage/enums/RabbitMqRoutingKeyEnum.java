package com.ruan.yuanyuan.mesage.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-27
 * Time: 18:53
 * version:
 * Description: 队列路由key
 */
public enum RabbitMqRoutingKeyEnum {


    ORDER_ROUTING_KEY("ORDER_ROUTING_KEY", "订单路由key"),
    PRODUCT_ROUTING_KEY("PRODUCT_ROUTING_KEY", "商品路由key"),
    ORDER_PAY_ROUTING_KEY("ORDER_PAY_ROUTING_KEY", "订单支付路由key"),
    TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY("TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY", "订单支付死信队列路由key"),

    ;

    RabbitMqRoutingKeyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String code;
    public String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RabbitMqRoutingKeyEnum getRabbitMqExchangeEnum(String name) {
        for (RabbitMqRoutingKeyEnum rabbitMqRoutingKeyEnum : RabbitMqRoutingKeyEnum.values()) {
            if (rabbitMqRoutingKeyEnum.getCode().equals(name)) {
                return rabbitMqRoutingKeyEnum;
            }
        }
        return null;
    }
}
