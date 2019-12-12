package com.ruan.yuanyuan.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-26
 * Time: 11:28
 * version:
 * Description:
 */
@SuppressWarnings("all")
public class ShiroLogOutFilter extends LogoutFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroLogOutFilter.class);

    @Override
    public boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("登出");
        //登出操作 清除缓存  subject.logout() 可以自动清理缓存信息, 这些代码是可以省略的  这里只是做个笔记 表示这种方式也可以清除
        Subject subject = getSubject(request, response);
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        //登出
        subject.logout();
        // 获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request, response, subject);
        // 重定向
        issueRedirect(request, response, redirectUrl);
        return false;

    }
}
