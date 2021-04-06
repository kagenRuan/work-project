package com.ruan.yuanyuan.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestAppliactionLister
 * @Author ruanyuanyuan
 * @Date 2020/8/28-20:24
 * @Version 1.0
 * @Description TODO 自定义Spring事件监听器
 **/
@Component
public class TestApplicationLister implements ApplicationListener<TestApplicationEvent>{


    @Override
    public void onApplicationEvent(TestApplicationEvent event) {
        System.out.println("自定义Spring事件被触发 id:"+event.getId()+",name="+event.getName());
    }
}
