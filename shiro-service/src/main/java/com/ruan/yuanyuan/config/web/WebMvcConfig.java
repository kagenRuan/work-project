package com.ruan.yuanyuan.config.web;

import com.ruan.yuanyuan.interceotor.MyHandlerInterceptor;
import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSFilter;
import com.thetransactioncompany.cors.autoreconf.AutoReconfigurableCORSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WebMvcConfig
 * @author: ruanyuanyuan
 * @date: 2020/7/5 18:49
 * @version: 1.0
 * @description: SpringMvc配置类
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

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
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyHandlerInterceptor())
//                .addPathPatterns("/**")
//                //以下路径不被拦截
//                .excludePathPatterns("/api/login/main","/api/login/logout","/api/login/genCaptcha")
//                ;
//    }
}
