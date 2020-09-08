package com.ruan.yuanyuan.she_ji_mo_shi.celuomoshi.simpl;

import java.math.BigDecimal;

/**
 * @ClassName ResultMessage
 * @Author ruanyuanyuan
 * @Date 2020/9/8-13:39
 * @Version 1.0
 * @Description TODO 返回对象
 **/
public class ResultMessage {

    public ResultMessage(String message, String code, BigDecimal amount) {
        this.message = message;
        this.code = code;
        this.amount = amount;
    }

    private String message;
    private String code;
    private BigDecimal amount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", amount=" + amount +
                '}';
    }
}
