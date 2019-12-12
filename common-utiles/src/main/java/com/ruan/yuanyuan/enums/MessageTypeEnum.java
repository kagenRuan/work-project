package com.ruan.yuanyuan.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 19:08
 * version:
 * Description:消息类型
 */
public enum MessageTypeEnum {

    ORDER("ORDER", "订单"),
    ORDER_PAY("ORDER_PAY", "支付订单");

    private String code;
    private String message;

    MessageTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static MessageTypeEnum getMessageTypeEnum(String name) {
        for (MessageTypeEnum type : MessageTypeEnum.values()) {
            if (type.getCode().equals(name)) {
                return type;
            }
        }
        return null;
    }

}
