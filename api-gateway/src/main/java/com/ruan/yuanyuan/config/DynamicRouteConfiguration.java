package com.ruan.yuanyuan.config;

import com.ruan.yuanyuan.service.IZuulApiRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

/**
 * 动态路由配置
 */
//@Configuration
public class DynamicRouteConfiguration {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;
    @Autowired
    private IZuulApiRouteService zuulApiRouteService;

    @Bean
    public DynamicRouteLocator routeLocator() {
        DynamicRouteLocator routeLocator = new DynamicRouteLocator(this.server.getServlet().getContextPath(), this.zuulProperties);
        routeLocator.setZuulApiRouteService(zuulApiRouteService);
        return routeLocator;
    }

}