package com.ruan.yuanyuan.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ruan.yuanyuan.vo.ResponseVo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * Created by yy on 2018/12/7.
 * url 拦截
 * 业务说明，可以分开对不同的需求进行url拦截，如果可以做到判断用户是否存在改资源功能
 * 说明，这里最好不要直接访问数据库，首先这里是服务的访问统一入口，所以会有一个服务边界的问题
 * 建议可以直接访问redis(缓存)
 */
//@Component
public class UrlFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    /**
     * 这里可以对资源进行判断
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        ResponseVo responseVo = new ResponseVo();
        String url = request.getRequestURI();
        if (url.contains("/order/getOrder") || url.contains("/product/getProductByIdProduct")) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        requestContext.setResponse(response);
        responseVo.setCode(401);
        responseVo.setMsg("没有权限");
        requestContext.setResponseBody(JSON.toJSONString(responseVo));
        return true;
    }

    /**
     * 这里进行业务逻辑处理
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
//        HttpServletResponse response = requestContext.getResponse();
//        ResponseVo responseVo = new ResponseVo();
//        response.setCharacterEncoding("UTF-8");
//        requestContext.setResponse(response);
//        responseVo.setCode(102);
//        Map<String, String[]> param = request.getParameterMap();
//        responseVo.setData(param);
//        requestContext.setResponseBody(JSON.toJSONString(responseVo));
        return null;
    }
}
