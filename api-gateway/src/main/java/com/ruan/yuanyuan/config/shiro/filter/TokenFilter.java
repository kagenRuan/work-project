package com.ruan.yuanyuan.config.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ruan.yuanyuan.vo.ResponseVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Created by yy on 2018/12/6.
 * 使用zuul鉴权(前置过滤器)
 */
//@Component
public class TokenFilter extends ZuulFilter {

    /**
     * 这个表示前置过滤器，也就是在请求前处理,进行参数校验
     *
     * @return{PRE_TYPE:pre 代表请求之前 post代表请求之后}
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }


    /**
     * 这个表示顺序，这个值越小越靠前
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 这里是过滤器逻辑,目前这里的逻辑是,如果请求url后面没有token参数则返回错误,没有权限
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        ResponseVo responseVo = new ResponseVo();
        String token = request.getParameter("token");
        if (!StringUtils.isNotBlank(token)) {
            //这里设置zuul响应为false
            requestContext.setSendZuulResponse(false);
            //设置响应httpStatus码为401(没有权限)
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponse(response);
            responseVo.setCode(401);
            responseVo.setMsg("没有权限");
            response.setCharacterEncoding("GBK");
            requestContext.setResponseBody(JSON.toJSONString(responseVo));
        }
        return null;
    }
}
