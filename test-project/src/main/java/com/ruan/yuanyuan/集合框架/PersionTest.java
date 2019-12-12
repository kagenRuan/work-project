package com.ruan.yuanyuan.集合框架;

public class PersionTest {

    private String name;

    public PersionTest(String name) {
        this.name = name;
    }

    /**
     * 基本数据类型
     */
    public void changeAge(int age) {
        age = 30;
    }

    /**
     * 对象引用
     *
     * @param p
     */
    public void setPersionName(PersionTest p) {
        p.setName("ryy");
    }

    /**
     * 字符串引用
     */
    public void getPersiongName(String str) {
        str = "rt";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        PersionTest persionTest = new PersionTest("ru");
        int age = 20;
        persionTest.changeAge(age);
        System.out.println(age);

        persionTest.setPersionName(persionTest);
        System.out.println(persionTest.getName());

        String str = "xxx";
        persionTest.getPersiongName(str);
        System.out.println(str);


    }
}
