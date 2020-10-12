package com.ruan.yuanyuan.testfinal;

/**
 * @ClassName TestFinal
 * @Author ruanyuanyuan
 * @Date 2020/8/17-22:43
 * @Version 1.0
 * @Description TODO 测试final 特点：具有不变形。
 * 什么是不变形：就说只要一个类被创建，并且里面的属性已经被赋值，那么就不能被更改
 **/
public class TestFinal {

    public static void main(String[] args) {

//        final TestFinal testFinal = new TestFinal(1,"YY");
//        //testFinal.age=2; final修饰所以不能修改，编译报错
//        testFinal.name="yy1";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("333");

    }


    public TestFinal(int age, String name) {
        this.age = age;
        this.name = name;
    }

    private final int age;
    private String name;



}
