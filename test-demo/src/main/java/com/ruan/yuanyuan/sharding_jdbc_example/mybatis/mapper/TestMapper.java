package com.ruan.yuanyuan.sharding_jdbc_example.mybatis.mapper;

import com.ruan.yuanyuan.sharding_jdbc_example.mybatis.entity.TestUser;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TestMapper
 * @Author ruanyuanyuan
 * @Date 2020/8/19-14:10
 * @Version 1.0
 * @Description TODO mybatis测试mapper
 **/
public interface TestMapper {

     TestUser queryTest(int age, @Param("id") String id);


}
