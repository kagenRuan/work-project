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

    private MyConfiguration myConfiguration;//配置类
    private MyExecutor myExecutor;//执行器

    /**
     * 执行单条查询方法
     * @param statementId
     * @param parameter
     * @param <T>
     * @return
     */
    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        String sql = "";
        return myExecutor.query(sql, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> clazz,MySqlSession sqlSession) {
        return (T) myConfiguration.getMapper(clazz,sqlSession);
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
