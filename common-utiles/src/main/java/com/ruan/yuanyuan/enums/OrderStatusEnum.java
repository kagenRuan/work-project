package com.ruan.yuanyuan.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 10:45
 * version:1.0
 * Description:订单状态
 */
public enum OrderStatusEnum {
    STAY_PAY("1", "待付款"),
    STAY_DELIVERY("2", "待发货"),
    COMPLETED("3", "已完成"),
    CANCEL("4", "已取消");
    private String code;
    private String message;

    OrderStatusEnum(String code, String message) {
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
