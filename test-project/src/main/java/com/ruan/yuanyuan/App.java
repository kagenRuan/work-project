package com.ruan.yuanyuan;

import com.ruan.yuanyuan.webconfig.TestConfig;
import com.ruan.yuanyuan.framework.context.RApplicationContext;
import com.ruan.yuanyuan.framework.servlet.RDispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import javax.servlet.ServletException;

/**
 * Hello world!
 */
//@SpringBootApplication
public class App {
    public static void main(String[] args) throws ServletException {

//        SpringApplication.run(App.class,args);
//          RApplicationContext context = new RApplicationContext("application.properties");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ruan.yuanyuan.config");

    }

//    @Bean  //一定要加，不然这个方法不会运行
//    public ServletRegistrationBean getServletRegistrationBean() {  //一定要返回ServletRegistrationBean
//        ServletRegistrationBean bean = new ServletRegistrationBean(new RDispatcherServlet("application.properties"));     //放入自己的Servlet对象实例
//        bean.addUrlMappings("/");  //访问路径值
//        return bean;
//    }
}
