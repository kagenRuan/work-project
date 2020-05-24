package com.ruan.yuanyuan.mybatis.config;

import com.ruan.yuanyuan.mybatis.proxy.MapperProxy;
import com.ruan.yuanyuan.mybatis.session.MySqlSession;

import java.lang.reflect.Proxy;

/**
 * @ClassName: MyConfiguration
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:18
 * @version: 1.0
 * @description:
 **/
public class MyConfiguration {

    /**
     * 返回接口的代理对象
     * @param clazz 被代理对象
     * @param <T> 返回代理后的对象
     * @return
     */
    public <T> T getMapper(Class<T> clazz, MySqlSession sqlSession) {
        /**
         * 第一个参数：类的加载器
         * 第二个参数：被代理对象
         * 第三个参数：实现了InvocationHandler接口的类
         */
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{clazz},new MapperProxy(clazz,sqlSession));
    }
}
