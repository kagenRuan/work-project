package com.ruan.yuanyuan.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruan.yuanyuan.entity.ZuulApiRoute;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.service.IZuulApiRouteService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 从数据库加载动态路由请求URL
 */
public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private IZuulApiRouteService zuulApiRouteService;
    private ZuulProperties properties;


    public void setZuulApiRouteService(IZuulApiRouteService zuulApiRouteService) {
        this.zuulApiRouteService = zuulApiRouteService;
    }

    public DynamicRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    /**
     * 查询所有的配置服务信息
     *
     * @return
     */
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        // 加载application.yml中的路由表
        routesMap.putAll(super.locateRoutes());
        // 加载db中的路由表
        routesMap.putAll(locateRoutesFromDB());

        // 统一处理一下路由path的格式
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }

        return values;
    }

    /**
     * 查询数据中的可用服务
     *
     * @return
     */
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("enabled", true);
        wrapper.eq("is_valid", Yum.YES.getCode());
        List<ZuulApiRoute> zuulApiRoutes = zuulApiRouteService.list(wrapper);

        for (ZuulApiRoute result : zuulApiRoutes) {
            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(zuulRoute, result);
                zuulRoute.setId(result.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

}