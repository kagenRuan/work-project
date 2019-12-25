package com.ruan.yuanyuan.mybatis.mapper.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestMapperXml
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:52
 * @version: 1.0
 * @description: 用于代替Mybatis的配置文件
 **/
public class TestMapperXml {

    public static final String nameSpace = "com.ruan.yuanyuan.mybatis.mapper.TestMapper";

    public static Map<String, String> methodMapping = new HashMap<>();

    static {
        methodMapping.put("selectById", "select * from user where id = %s");
    }
}
