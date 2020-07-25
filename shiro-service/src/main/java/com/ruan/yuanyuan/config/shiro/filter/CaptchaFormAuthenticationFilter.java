package com.ruan.yuanyuan.config.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CaptchaFormAuthenticationFilter
 * @author: ruanyuanyuan
 * @date: 2020/7/5 15:47
 * @version: 1.0
 * @description: 表单拦截器
 **/
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        logger.info("过滤掉嗅探OPTIONS请求");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            return true;
        }else if(null == SecurityUtils.getSubject().getPrincipal()){
            return false;
        }
        String token = httpServletRequest.getHeader("authorization");
        logger.debug("请求token:{}",token);
        return super.isAccessAllowed(servletRequest, servletResponse, o);
    }

}



