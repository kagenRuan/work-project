package com.ruan.yuanyuan.mesage.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-27
 * Time: 18:44
 * version:
 * Description: 队列交换机
 */
public enum RabbitMqExchangeEnum {

    ORDER_EXCHANGE("ORDER_EXCHANGE","订单交换机"),
    PRODUCT_EXCHANGE("PRODUCT_EXCHANGE","商品交换机"),
    ORDER_PAY_EXCHANGE("ORDER_PAY_EXCHANGE","支付订单交换机"),
    TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE("TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE","支付订单死信交换机"),

    ;

    RabbitMqExchangeEnum(String code, String name) {
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


    public static RabbitMqExchangeEnum getRabbitMqExchangeEnum(String name){
        for(RabbitMqExchangeEnum rabbitMqExchangeEnum :RabbitMqExchangeEnum.values()){
            if(rabbitMqExchangeEnum.getCode().equals(name)){
                return rabbitMqExchangeEnum;
            }
        }
        return null;
    }
}
