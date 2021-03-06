package com.ruan.yuanyuan.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-16
 * Time: 14:07
 * version:
 * Description: 用户类型枚举
 */
public enum UserTypeEnum {

    ADMIN("1", "管理员"),
    USER("2", "普通用户");

    private String code;
    private String message;

    UserTypeEnum(String code, String message) {
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
