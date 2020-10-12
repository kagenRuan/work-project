package com.ruan.yuanyuan.thread.callableAndRunable;

import java.util.concurrent.Callable;

/**
 * @ClassName TestCallable
 * @Author ruanyuanyuan
 * @Date 2020/8/17-15:29
 * @Version 1.0
 * @Description TODO 实现Callable接口
 * 特点：有返回值，可以直接throw Exception
 **/
public class TestCallable implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("aaa");
        return "sss";
    }
}
