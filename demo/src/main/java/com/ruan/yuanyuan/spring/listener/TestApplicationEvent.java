package com.ruan.yuanyuan.spring.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName TestApplicationEvent
 * @Author ruanyuanyuan
 * @Date 2020/8/28-20:27
 * @Version 1.0
 * @Description TODO 自定义Spring事件
 **/
public class TestApplicationEvent extends ApplicationEvent {

    private String id;
    private String name;

    public TestApplicationEvent(Object source) {
        super(source);
    }

    public TestApplicationEvent(Object source, String id, String name) {
        super(source);
        this.id = id;
        this.name = name;
    }

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
}
