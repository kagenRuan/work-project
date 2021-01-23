package com.ruan.yuanyuan.config.web.interceotor;

import com.alibaba.fastjson.JSONObject;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            responseMsg(response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("<<<<<<<<< 接口总耗时时间  >>>>>>>>> date:{}",System.currentTimeMillis() -  preDate);
    }


    private void  responseMsg(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        ResultObject resultObject = new ResultObject(404,"未登陆请登陆");
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(resultObject));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
