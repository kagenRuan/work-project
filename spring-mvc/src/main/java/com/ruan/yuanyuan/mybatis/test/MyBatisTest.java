package com.ruan.yuanyuan.mybatis.test;

import com.ruan.yuanyuan.mybatis.mapper.TestMapper;
import com.ruan.yuanyuan.mybatis.session.MySqlSession;
import com.ruan.yuanyuan.mybatis.session.impl.DefaultMySqlSession;

import java.sql.ResultSet;

/**
 * @ClassName: MyBatisTest
 * @author: ruanyuanyuan
 * @date: 2019/12/22 16:08
 * @version: 1.0
 * @description: Mybatis 测试
 **/
public class MyBatisTest {

    public static void start() {
        MySqlSession sqlSession = new DefaultMySqlSession();
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Object result = testMapper.selectById("1");
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        start();
    }
}
