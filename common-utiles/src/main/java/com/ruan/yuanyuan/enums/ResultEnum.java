package com.ruan.yuanyuan.enums;

/**
 * @ClassName: ResultEnum
 * @author: ruanyuanyuan
 * @date: 2019/12/26 14:57
 * @version: 1.0
 * @description: 返回枚举
 **/
public enum ResultEnum {
    SUCCESS(true, "操作成功",1),
    FAIL(false,"操作失败",-9999)
    ;
    private boolean name;
    private String  msg;
    private int     code;

    ResultEnum(boolean name, String msg,int code) {
        this.name = name;
        this.msg = msg;
        this.code=code;
    }

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResultEnum getResultEnum(boolean name){
        ResultEnum[] resultEnums = ResultEnum.values();
        for(ResultEnum resultEnum:resultEnums){
            if(resultEnum.name == name){
                return resultEnum;
            }
        }
        return ResultEnum.FAIL;
    }
}
