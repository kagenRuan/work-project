package com.ruan.yuanyuan.mesage.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-27
 * Time: 18:45
 * version:
 * Description: 队列枚举
 */
public enum RabbitMqQueueEnum {

    ORDER_QUEUE("ORDER_QUEUE", "订单队列"),
    PRODUCT_QUEUE("PRODUCT_QUEUE", "商品队列"),
    ORDER_PAY_QUEUE("ORDER_PAY_QUEUE", "订单支付队列"),
    TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_QUEUE("TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_QUEUE", "支付订单死信队列生产端"),
    TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_QUEUE("TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_QUEUE", "支付订单死信队列消费端");

    RabbitMqQueueEnum(String code, String name) {
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
}
