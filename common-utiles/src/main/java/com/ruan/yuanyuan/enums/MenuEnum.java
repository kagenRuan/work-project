package com.ruan.yuanyuan.enums;

/**
 * @ClassName: MenuEnum
 * @author: ruanyuanyuan
 * @date: 2020/7/6 10:55
 * @version: 1.0
 * @description: 菜单枚举
 **/
public enum MenuEnum {

    BUTTON("0", "按钮"),
    MENU("1", "菜单");
    private String code;
    private String message;

    MenuEnum(String code, String message) {
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
