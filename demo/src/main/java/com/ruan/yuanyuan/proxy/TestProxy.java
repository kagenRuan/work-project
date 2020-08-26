package com.ruan.yuanyuan.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName TestProxy
 * @Author ruanyuanyuan
 * @Date 2020/8/25-23:26
 * @Version 1.0
 * @Description TODO JDK动态代理类
 **/
public class TestProxy implements InvocationHandler {

    private Object target;

    public TestProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理");
        method.invoke(target,args);
        return null;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
}
