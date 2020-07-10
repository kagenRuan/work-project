package com.ruan.yuanyuan.enums;

/**
 * @ClassName: PermissionsType
 * @author: ruanyuanyuan
 * @date: 2020/7/8 19:06
 * @version: 1.0
 * @description: 资源枚举
 **/
public enum  PermissionsTypeEnum {

    CATALOGUE("0", "目录"),
    MENU("1", "菜单");
    private String code;
    private String message;

    PermissionsTypeEnum(String code, String message) {
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
