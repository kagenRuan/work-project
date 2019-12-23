package com.ruan.yuanyuan.mybatis.proxy;

import com.ruan.yuanyuan.mybatis.mapper.xml.TestMapperXml;
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

    private final MySqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(MySqlSession sqlSession, Class<T> clazz) {
        this.sqlSession = sqlSession;
        this.mapperInterface = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().getName().equals(TestMapperXml.nameSpace)) {
            String sql = TestMapperXml.methodMapping.get(method.getName());
            System.out.println("sql=" + sql);
            return sqlSession.selectOne(sql, String.valueOf(args[0]));
        }
        return null;
    }
}
