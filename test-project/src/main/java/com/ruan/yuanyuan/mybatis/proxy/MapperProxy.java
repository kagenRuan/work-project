package com.ruan.yuanyuan.mybatis.proxy;

import com.ruan.yuanyuan.mybatis.session.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName: MapperProxy
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:33
 * @version: 1.0
 * @description: JDK的动态代理
 **/
public class MapperProxy<T> implements InvocationHandler {

    private  MySqlSession sqlSession;
    private  Class<T> mapperInterface;

    public MapperProxy() {

    }

    public MapperProxy(Class<T> clazz,MySqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.mapperInterface = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return sqlSession.selectOne();
    }
}
