package com.ruan.yuanyuan.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestMapper {

    void queryVoidById(@Param("id") String id);

    List queryListById(@Param("id") String id);

    Map queryMapById(@Param("id") String id);

    Object queryById(@Param("id") String id,@Param("name") String name);

    Object updateById(@Param("id")String id);

    Object insertTest(@Param("id")String id,@Param("name")String name);

    Object deleteById(@Param("id")String id);
}
