package com.ruan.yuanyuan.enums;

/**
 * @author yy
 * @date 2019/1/8.
 * 返回枚举
 */
public enum ResultObjectEnum {

    SUCCESS("SUCCESS", 0,"成功"),
    FAIL("FAIL", 1,"失败"),
    NOT_TOKEN("NOT_TOKEN", 401,"Token不存在"),
    SYSTEM_HYSTRIX("SYSTEM_HYSTRIX",-99999,"接口不可用，进行接口降级")
    ;

    ResultObjectEnum(String name, Integer code,String message) {
        this.name = name;
        this.code = code;
        this.message=message;
    }

    private String name;
    private Integer code;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
