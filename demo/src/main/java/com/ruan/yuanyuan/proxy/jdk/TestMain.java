package com.ruan.yuanyuan.proxy.jdk;

/**
 * @ClassName TestMain
 * @Author ruanyuanyuan
 * @Date 2020/8/25-23:28
 * @Version 1.0
 * @Description TODO
 **/
public class TestMain {

    public static void main(String[] args) {
        TestPrintln testPrintln = new TestProxy(new TestPrintImpl()).getProxy();
        testPrintln.testPrintln();
    }
}
