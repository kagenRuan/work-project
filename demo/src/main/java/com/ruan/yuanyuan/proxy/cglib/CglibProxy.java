package com.ruan.yuanyuan.proxy.cglib;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName CglibInterceptor
 * @Author ruanyuanyuan
 * @Date 2020/8/28-09:51
 * @Version 1.0
 * @Description TODO
 **/
public class CglibProxy implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib say hello");
        methodProxy.invokeSuper(o,objects);
        return o;
    }
}
