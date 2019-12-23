package com.ruan.yuanyuan.mybatis.session.impl;

import com.ruan.yuanyuan.mybatis.config.MyConfiguration;
import com.ruan.yuanyuan.mybatis.executor.MyExecutor;
import com.ruan.yuanyuan.mybatis.executor.impl.MySimpleExecutor;
import com.ruan.yuanyuan.mybatis.proxy.MapperProxy;
import com.ruan.yuanyuan.mybatis.session.MySqlSession;

import java.lang.reflect.Proxy;

/**
 * @ClassName: DefaultMySqlSession
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:23
 * @version: 1.0
 * @description: MySqlSession的实现类
 **/
public class DefaultMySqlSession implements MySqlSession {

    private MyConfiguration myConfiguration;
    private MyExecutor myExecutor = new MySimpleExecutor();

//    public DefaultMySqlSession(MyConfiguration myConfiguration, MyExecutor myExecutor) {
//        this.myConfiguration = myConfiguration;
//        this.myExecutor = myExecutor;
//    }


    @Override
    public <T> T selectOne(String statement, String parameter) {
        return myExecutor.excute(statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> obj) {
        return (T) Proxy.newProxyInstance(obj.getClassLoader(), new Class[]{obj}, new MapperProxy(this, obj));
    }

    @Override
    public MyConfiguration getConfiguration() {
        return this.myConfiguration;
    }

    public MyConfiguration getMyConfiguration() {
        return myConfiguration;
    }

    public MyExecutor getMyExecutor() {
        return myExecutor;
    }

}
