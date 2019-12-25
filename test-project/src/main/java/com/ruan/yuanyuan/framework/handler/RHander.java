package com.ruan.yuanyuan.framework.handler;

import java.lang.reflect.Method;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 14:54
 * version:
 * Description:
 */
public class RHander {

    public Object controller;
    public Method method;

    public RHander(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }
}
