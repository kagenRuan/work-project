package com.ruan.yuanyuan.mesage.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-27
 * Time: 18:45
 * version:
 * Description: 队列交换机类型
 */
public enum  RabbitMqExchangeTypeEnum {


    DIRECT("DIRECT","direct"),
    TOPIC("TOPIC","topic"),

    ;

    RabbitMqExchangeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

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
