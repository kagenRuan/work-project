package com.ruan.yuanyuan.sharding_jdbc_example.mybatis.test;

import com.ruan.yuanyuan.sharding_jdbc_example.mybatis.entity.TestUser;
import com.ruan.yuanyuan.sharding_jdbc_example.mybatis.mapper.TestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName TestMain
 * @Author ruanyuanyuan
 * @Date 2020/8/19-14:20
 * @Version 1.0
 * @Description TODO
 **/
public class TestMain {

    public static void main(String[] args) throws IOException {
        // 读取mybatis-config.xml文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        // 初始化mybatis，创建SqlSessionFactory类的实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 创建Session实例
        SqlSession session = sqlSessionFactory.openSession();
        // 获得mapper接口的代理对象
        TestMapper testMapper = session.getMapper(TestMapper.class);
        TestUser testUser = testMapper.queryTest(11,"1");
        System.out.println(testUser);
//        Map par = new HashMap();
//        par.put("id","1");
//        TestUser testUser1 = session.selectOne("com.ruan.yuanyuan.mybatis.mapper.TestMapper.queryTest", par);
//        System.out.println(testUser1);
    }
}
