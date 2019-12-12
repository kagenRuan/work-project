package com.ruan.yuanyuan.exception;

/**
 * User: ruanyuanyuan
 * Date: 2019-07-26
 * Time: 15:50
 * version:
 * Description: 业务异常类
 */
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(Integer code, String message, String... arg) {
        this.code = code;
        this.message = String.format(message, arg);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
