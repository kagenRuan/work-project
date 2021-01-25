package io.renren.modules.app.enumutils;

/**
 * @ClassName UserEnum
 * @Author ruanyuanyuan
 * @Date 2021/1/24-17:46
 * @Version 1.0
 * @Description TODO  订单状态枚举类
 **/
public enum OrderStatusEnum {

    NOT_PAY(1, "未付款"),
    PAY(2, "已付款"),
    YI_FA_HUO(3, "已发货"),
    YI_QIAN_SHOU(4, "已签收"),
    CANCEL(5, "已取消");

    private Integer code;
    private String message;

    ;

    OrderStatusEnum(Integer code, String message) {
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
