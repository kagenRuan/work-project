package com.ruan.yuanyuan.mybatis.session;

import com.ruan.yuanyuan.mybatis.config.MyConfiguration;

/**
 * @ClassName: MySqlSession
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:17
 * @version: 1.0
 * @description:
 **/
public interface MySqlSession {

    //查询方法
    <T> T selectOne(String statementId, Object parameter);

    //获取Mapper
    <T> T getMapper(Class<T> clazz,MySqlSession sqlSession);

    MyConfiguration getConfiguration();

}
