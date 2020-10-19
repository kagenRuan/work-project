package com.ruan.yuanyuan.config;

import com.ruan.yuanyuan.listener.InitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ProductConfig
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:22
 * @Version 1.0
 * @Description TODO
 **/
@Configuration
public class ProductConfig {

    /**
     * @Description: 注册监听器
     * @return: org.springframework.boot.web.servlet.ServletListenerRegistrationBean
     **/
    @Bean
    public ServletListenerRegistrationBean initListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitListener());
        return servletListenerRegistrationBean;
    }
}
