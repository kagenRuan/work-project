package com.ruan.yuanyuan.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-16
 * Time: 14:07
 * version:
 * Description: 用户类型枚举
 */
public enum UserTypeEnum {

    ADMIN(1,"管理员"),
    USER(2,"普通用户")
    ;

    private Integer code;
    private String message;

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
