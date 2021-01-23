package com.ruan.yuanyuan.spring.refresh;

import org.springframework.beans.BeansException;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * @ClassName TestRefresh
 * @Author ruanyuanyuan
 * @Date 2020/9/11-15:50
 * @Version 1.0
 * @Description TODO
 **/
public class TestRefreshContext extends GenericWebApplicationContext
        implements ConfigurableWebServerApplicationContext {

    @Override
    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            throw ex;
        }
    }

    @Override
    protected void onRefresh() throws BeansException {

        super.onRefresh();
        try {
            System.out.println("刷新上下文");
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    @Override
    public void setServerNamespace(String serverNamespace) {

    }

    @Override
    public WebServer getWebServer() {
        return null;
    }

    @Override
    public String getServerNamespace() {
        return null;
    }
}
