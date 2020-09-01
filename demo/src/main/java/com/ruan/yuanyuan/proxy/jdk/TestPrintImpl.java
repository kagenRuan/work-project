package com.ruan.yuanyuan.proxy.jdk;

/**
 * @ClassName TestPrintImpl
 * @Author ruanyuanyuan
 * @Date 2020/8/25-23:35
 * @Version 1.0
 * @Description TODO
 **/
public class TestPrintImpl implements TestPrintln {

    @Override
    public void testPrintln() {
        System.out.println("testPrintln");
    }
}
