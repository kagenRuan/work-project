package com.ruan.yuanyuan.mybatis.executor;

/**
 * @ClassName: Executor
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:18
 * @version: 1.0
 * @description:
 **/
public interface MyExecutor {


    <E> E excute(String statement, String parameter);
}
