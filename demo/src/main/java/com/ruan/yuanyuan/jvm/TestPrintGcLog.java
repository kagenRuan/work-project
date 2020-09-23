package com.ruan.yuanyuan.jvm;

/**
 * @ClassName TestPrintGcLog
 * @Author ruanyuanyuan
 * @Date 2020/9/18-17:15
 * @Version 1.0
 * @Description TODO 打印GC日志以及分析GC日志
 **/
public class TestPrintGcLog {

    public static void main(String[] args) {
        byte[] bytes = new byte[1024 * 1024];
        bytes = new byte[1024 * 1024];
        bytes = new byte[1024 * 1024];
        bytes = null;
        byte[] bytes1 = new byte[1024 * 1024];
    }
}
