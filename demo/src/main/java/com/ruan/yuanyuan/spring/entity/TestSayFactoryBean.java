package com.ruan.yuanyuan.spring.entity;

/**
 * @ClassName TestSayFactoryBean
 * @Author ruanyuanyuan
 * @Date 2020/8/28-21:01
 * @Version 1.0
 * @Description TODO 用于测试FactoryBean
 **/
public class TestSayFactoryBean {

    public TestSayFactoryBean() {
    }

    public TestSayFactoryBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
