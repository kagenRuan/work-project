package com.ruan.yuanyuan.exception;

/**
 * User: ruanyuanyuan
 * Date: 2019-07-26
 * Time: 15:43
 * version:
 * Description: 异常方法定义
 */
public interface ExceptionInterface {

    /**
     * 用于获取异常code码
     * @return code
     */
    int getCode();

    /**
     * 用于获取异常信息描述
     * @return message
     */
    String getMessage();

}
