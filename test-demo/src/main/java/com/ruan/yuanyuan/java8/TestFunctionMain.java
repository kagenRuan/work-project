package com.ruan.yuanyuan.java8;

import org.springframework.context.annotation.Bean;

public class TestFunctionMain {


    public static void test(String name,TestFunction testFunction){
        testFunction.testNoParam(name);
    }

    public static void main(String[] args) {
        TestFunctionMain.test("ryy",name ->{
            System.out.println(name);
        });
    }
}
