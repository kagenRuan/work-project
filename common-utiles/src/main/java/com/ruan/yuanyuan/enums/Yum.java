package com.ruan.yuanyuan.enums;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-21
 * Time: 08:46
 * version:
 * Description:
 */
public enum Yum {

    YES("Y", "有效"),
    NO("N", "无效");
    private String code;
    private String message;

    Yum(String code, String message) {
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
