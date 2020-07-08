package com.ruan.yuanyuan.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CrosFilter
 * @author: ruanyuanyuan
 * @date: 2020/7/7 19:43
 * @version: 1.0
 * @description:
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${allowed.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins(allowedOrigins)
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers", "access-control-allow-methods", "access-control-allow" +
                        "-origin", "access-control-max-age", "X-Frame-Options","authorization")
                //跨域允许时间
                .maxAge(36000);
    }
}
