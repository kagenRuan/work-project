package com.ruan.yuanyuan.spring.refresh;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * @ClassName TestRefresh
 * @Author ruanyuanyuan
 * @Date 2020/9/11-15:50
 * @Version 1.0
 * @Description TODO
 **/
@Component
public class TestRefresh extends AnnotationConfigApplicationContext {

    @Override
    protected void onRefresh() throws BeansException {
        super.onRefresh();
        System.out.println("刷新上下文");
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        super.refresh();
    }
}
