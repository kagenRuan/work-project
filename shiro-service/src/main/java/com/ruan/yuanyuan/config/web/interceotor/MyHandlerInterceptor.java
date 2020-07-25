package com.ruan.yuanyuan.config.web.interceotor;

import com.alibaba.fastjson.JSONObject;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: MyHandlerInterceptor
 * @author: ruanyuanyuan
 * @date: 2020/7/5 19:12
 * @version: 1.0
 * @description: 自定义拦截器
 **/
public class MyHandlerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    long preDate = System.currentTimeMillis();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("<<<<<<<<<< 接口请求路径   >>>>>>>>> url:{} params：{} ",request.getRequestURI(),JSONObject.toJSONString(request.getParameterMap()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("<<<<<<<<< 接口总耗时时间  >>>>>>>>> date:{}",System.currentTimeMillis() -  preDate);
    }
}
