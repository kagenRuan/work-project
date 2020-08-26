package com.ruan.yuanyuan.mybatis.entity;

/**
 * @ClassName TestUser
 * @Author ruanyuanyuan
 * @Date 2020/8/19-14:17
 * @Version 1.0
 * @Description TODO
 **/
public class TestUser {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
