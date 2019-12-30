package com.ruan.yuanyuan.enums;

/**
 * @ClassName: PayStatusEnum
 * @author: ruanyuanyuan
 * @date: 2019/12/27 14:51
 * @version: 1.0
 * @description: 支付状态
 **/
public enum PayStatusEnum {

    STAY_PAY("1","待支付"),
    WAIT_PAY("2","支付中"),
    ALREADY_PAY("3","已支付")
    ;

    private String code;
    private String message;

    PayStatusEnum(String code, String message) {
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
}
