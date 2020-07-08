package com.ruan.yuanyuan.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruan.yuanyuan.enums.ResultObjectEnum;
import com.ruan.yuanyuan.exception.ExceptionUtil;

import java.io.Serializable;

/**
 * @author yy
 * @date 2019/1/8.
 * 返回对象
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResultObject implements Serializable {

    private Object data;

    private Integer code;

    private String msg;

    public ResultObject() {
        this.code = ExceptionUtil.SystemExceptionEnum.SUCCESS.getCode();
        this.msg = ExceptionUtil.SystemExceptionEnum.SUCCESS.getMessage();
    }

    public ResultObject(Object data) {
        this.data = data;
        this.code = ResultObjectEnum.SUCCESS.getCode();
    }

    public ResultObject(Object data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public ResultObject(String msg) {
        this.msg = msg;
        this.code = ExceptionUtil.SystemExceptionEnum.SUCCESS.getCode();
    }

    public ResultObject(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
