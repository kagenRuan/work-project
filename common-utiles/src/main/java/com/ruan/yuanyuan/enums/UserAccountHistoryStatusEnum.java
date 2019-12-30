package com.ruan.yuanyuan.enums;

/**
 * @ClassName: TccStatusEnum
 * @author: ruanyuanyuan
 * @date: 2019/12/27 15:40
 * @version: 1.0
 * @description: TCC 事务状态
 **/
public enum UserAccountHistoryStatusEnum {

    TRYING("1", "尝试"),
    CANCEL("2","取消"),
    CONFIRM("3","确认")
    ;

    private String code;
    private String message;

    UserAccountHistoryStatusEnum(String code, String message) {
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
