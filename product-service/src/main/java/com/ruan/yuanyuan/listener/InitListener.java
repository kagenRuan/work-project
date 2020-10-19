package com.ruan.yuanyuan.listener;

import com.ruan.yuanyuan.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName InitListener
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:20
 * @Version 1.0
 * @Description TODO 系统初始化监听器
 **/
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //TODO 初始化单例对象
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
