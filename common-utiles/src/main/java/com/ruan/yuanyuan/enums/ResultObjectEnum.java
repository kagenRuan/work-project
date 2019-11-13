package com.ruan.yuanyuan.enums;

/**
 * @author yy
 * @date 2019/1/8.
 * 返回枚举
 */
public enum ResultObjectEnum {

    SUCCESS("SUCCESS",0),
    FAIL("FAIL",1),
    NOT_TOKEN("NOT_TOKEN",401),
    ;

    ResultObjectEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    private String name;
    private  Integer code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
