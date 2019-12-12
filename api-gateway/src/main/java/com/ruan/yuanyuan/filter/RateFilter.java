package com.ruan.yuanyuan.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ruan.yuanyuan.vo.ResponseVo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * @author ceshi
 * @Title: 限流过滤器
 * @Package ${package_name}
 * @Description: 需要放在请求的最前面也就是前置过滤器, 请求过来是就需要进入到这个filter
 * @date 2018/12/621:52
 */
//@Component
public class RateFilter extends ZuulFilter {
    /**
     * 这里使用的是goole guava的令牌通jar包,这里表示每秒创建100个令牌
     */
    RateLimiter rateLimiter = RateLimiter.create(5);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 这里的业务逻辑，由于令牌桶的限制，如果没有获得令牌怎返回结果
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();
        ResponseVo responseVo = new ResponseVo();
        if (!rateLimiter.tryAcquire()) {
            requestContext.setSendZuulResponse(false);
            response.setCharacterEncoding("UTF-8");
            requestContext.setResponse(response);
            responseVo.setCode(101);
            responseVo.setMsg("未拿到令牌，不能抢购");
            requestContext.setResponseBody(JSON.toJSONString(responseVo));
        }
        return null;
    }
}
