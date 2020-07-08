package com.ruan.yuanyuan.config.session;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @ClassName: MySessionManager
 * @author: ruanyuanyuan
 * @date: 2020/7/7 20:09
 * @version: 1.0
 * @description:
 **/
public class MySessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //从前端ajax headers中获取这个参数用来判断授权
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (StringUtils.hasLength(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //从前端的cookie中取值
            return super.getSessionId(request, response);
        }

    }
}
