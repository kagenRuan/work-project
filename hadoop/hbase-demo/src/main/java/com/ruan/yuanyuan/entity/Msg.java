package com.ruan.yuanyuan.entity;

public class Msg {

    private String rowKey;
    private String name;
    private String age;
    private long data;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "rowKey='" + rowKey + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", data=" + data +
                '}';
    }
}
