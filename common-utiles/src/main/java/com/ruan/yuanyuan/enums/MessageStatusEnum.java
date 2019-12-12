package com.ruan.yuanyuan.enums;

import org.springframework.util.StringUtils;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 17:24
 * version:
 * Description:消息状态枚举
 */
public enum MessageStatusEnum {

    NOT_DELIVERY("1", "消息未投递"),
    SED_DELIVERY("2", "消息投递中"),
    CAN_DELIVERY("3", "消息已投递"),
    MESSAGE_DELIVERY_FAIL("4", "消息投递失败"),
    ALREADY_MESSAGE("5", "消息已消费"),
    MESSAGE_EXCEPTION("6", "消息异常");

    public String code;
    public String message;

    MessageStatusEnum(String code, String message) {
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

    public static MessageStatusEnum getEnum(String name) {
        if (!StringUtils.isEmpty(name)) {
            for (MessageStatusEnum messageStatusEnum : MessageStatusEnum.values()) {
                if (messageStatusEnum.getCode().equals(name)) {
                    return messageStatusEnum;
                }
            }
        }
        return null;
    }
}
