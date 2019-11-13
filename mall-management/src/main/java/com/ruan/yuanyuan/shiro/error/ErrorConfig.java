package com.ruan.yuanyuan.shiro.error;

import org.springframework.boot.web.server.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-26
 * Time: 12:02
 * version:
 * Description: 解决spring-boot Whitelabel Error Page
 * 例如404，504错误跳转页面
 */
@Component
public class ErrorConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {


    /**
     * /error/noAuth | /error/404 | /error/500 配置的是ErrorPageController控制器中的请求路径
     * @param factory
     */
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        Set<ErrorPage> errorPageSet = new HashSet<>();
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage error405Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/405");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        errorPageSet.add(error400Page);
        errorPageSet.add(error401Page);
        errorPageSet.add(error404Page);
        errorPageSet.add(error500Page);
        errorPageSet.add(error405Page);
        factory.setErrorPages(errorPageSet);

    }
}
