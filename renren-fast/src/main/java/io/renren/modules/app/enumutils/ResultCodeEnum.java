package io.renren.modules.app.enumutils;

/**
 * @ClassName UserEnum
 * @Author ruanyuanyuan
 * @Date 2021/1/24-17:46
 * @Version 1.0
 * @Description TODO  接口返回状态枚举类
 **/
public enum ResultCodeEnum {

    SUCCESS(1, "SUCCESS"),
    FAIL(2, "FAIL"),
    TRADE_FINISHED(3,"TRADE_FINISHED"),
    TRADE_SUCCESS(4,"TRADE_SUCCESS")
    ;
    private Integer code;
    private String message;

    ;

    ResultCodeEnum(Integer code, String message) {
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
