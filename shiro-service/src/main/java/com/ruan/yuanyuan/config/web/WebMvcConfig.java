package com.ruan.yuanyuan.config.web;

import com.ruan.yuanyuan.config.web.interceotor.MyHandlerInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import java.util.Collections;

/**
 * @ClassName: WebMvcConfig
 * @author: ruanyuanyuan
 * @date: 2020/7/5 18:49
 * @version: 1.0
 * @description: SpringMvc配置类
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static String PROJECT_NAME="mall-manager";

    /**
     * 设置上传文件大小配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        DataSize dataSize = DataSize.ofBytes(10000);
        //上传文件大小超出将抛出异常
        factory.setMaxFileSize(dataSize);
        //上传文件总的大小
        DataSize requestDataSize = DataSize.ofBytes(10000);
        factory.setMaxRequestSize(requestDataSize);
        return factory.createMultipartConfig();
    }



    /**
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor())
                .addPathPatterns("/**")
                //以下路径不被拦截
                .excludePathPatterns("/api/login/main","/api/login/logout","/api/login/genCaptcha")
                ;
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
//        bean.setUrlPatterns(Collections.singletonList(PROJECT_NAME + "/*"));
//        return bean;
//    }
}
