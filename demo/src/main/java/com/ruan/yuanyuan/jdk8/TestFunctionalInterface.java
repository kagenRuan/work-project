package com.ruan.yuanyuan.jdk8;

/**
 * @ClassName TestFunctionalInterface
 * @Author ruanyuanyuan
 * @Date 2020/9/13-16:14
 * @Version 1.0
 * @Description TODO 测试JDKFunctionalInterface注解
 **/
@FunctionalInterface
public interface TestFunctionalInterface {

     void test();
}

class TestFunctionalInterfaceImpl implements TestFunctionalInterface{

    @Override
    public void test() {
        System.out.println("TestFunctionalInterface的接口实现");
    }
}

class Test{

    public void test(String name,TestFunctionalInterface functionalInterface){
        System.out.println(name);
        functionalInterface.test();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.test("yy",() ->{
            System.out.println("ccc");
        });
    }
}
