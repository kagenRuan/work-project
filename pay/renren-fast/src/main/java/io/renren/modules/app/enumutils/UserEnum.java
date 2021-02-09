package io.renren.modules.app.enumutils;

/**
 * @ClassName UserEnum
 * @Author ruanyuanyuan
 * @Date 2021/1/24-17:46
 * @Version 1.0
 * @Description TODO  用户类型枚举类
 **/
public enum UserEnum {

    ORDINARY_USER(1, "普通用户"),
    WEI_XIN_USER(2, "微信用户"),
    ALI_USER(3, "支付宝用户");
    private Integer code;
    private String message;

    ;

    UserEnum(Integer code, String message) {
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
    }
}
