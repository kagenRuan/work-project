package com.ruan.yuanyuan.demo.thread;

/**
 * @ClassName TestRunable
 * @Author ruanyuanyuan
 * @Date 2020/8/17-15:26
 * @Version 1.0
 * @Description TODO 实现Runable接口
 * 特点：Runable接口 没有返回值，不能直接throw Exception异常,除非使用try-cache
 *      就算能抛异常也无法捕捉，因为调用run方法不是我们自己调用的，而是JAVA调用的
 **/
public class TestRunable implements Runnable {

    @Override
    public void run() {
        //只能这样抛异常，但是就算抛异常也无法捕捉到
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
