package com.ruan.yuanyuan.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ruan.yuanyuan.shiro.filter.ShiroConcurrentLoginFilter;
import com.ruan.yuanyuan.shiro.matcher.RetryLimitHashedCredentialsMatcher;
import com.ruan.yuanyuan.shiro.realm.ShiroRealm;
import com.ruan.yuanyuan.shiro.session.ShiroSessionListener;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.*;


/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 15:56
 * version: 1.0.0
 * Description:  shiro配置类
 */
@Configuration
@SuppressWarnings("all")
public class ShiroConfig {


    /**
     * 配置thymeleaf整合shiro标签
     */
    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * 在没有权限时针对shiro不同的异常跳转不同的页面
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver  simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","redirect:/noAuth");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }


    /**
     * 添加shiro注解 第二步
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
       return authorizationAttributeSourceAdvisor;
    }




    /**
     * 创建ShiroFilterFbean 第三步
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //登录请求路径，非登录页面路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        //这里的/mall/doLogin是后台的接口名,非页面,登录成功后要跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/menuNav");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        //自定义拦截器限制并发人数
        LinkedHashMap<String, Filter> concerenMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
//        concerenMap.put("kickout", kickoutSessionControlFilter());
        //自定义登出过滤器
        concerenMap.put("logout",logoutFilter());

        shiroFilterFactoryBean.setFilters(concerenMap);

        /**
         * 这里的的几个角色需要很清楚
         * shiro过滤器：可以实现拦截相关权限
         *      常用过滤器：
         *             anon:无需认证可以直接访问
         *             authc:必须认证才能访问
         *             user:如果使用rememberMe的功能才可以访问
         *             perms:该资源必须得到资源权限才能访问
         *             roles:该资源必须得到角色权限才能访问
         */

        /**
         * 初始化需要拦截的路径
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        /**
         * 这里需要注意：当使用【perms】过滤器当拦截后，如果未授权会自动跳转到未授权页面
         */
//        filterMap.put("/login","kickout,anon");
        filterMap.put("/login","anon");
        filterMap.put("/static/**", "anon");
        //logout是shiro提供的过滤器
        filterMap.put("/logout","logout");
        filterMap.put("/**","authc");
//        filterMap.put("/**","kickout,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager 第一步
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //配置记住我
//        securityManager.setRememberMeManager(rememberMeManager());
        //配置 ehcache缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        //配置自定义session管理，使用ehcache
        securityManager.setSessionManager(sessionManager());
        //这里需要关联Realm
        securityManager.setRealm(getRealm());
        return securityManager;
    }

    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
         return new LifecycleBeanPostProcessor(); }

    /**
     * 创建Realm
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm getRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        shiroRealm.setAuthenticationCachingEnabled(true);
        shiroRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        shiroRealm.setAuthorizationCachingEnabled(true);
        shiroRealm.setAuthorizationCacheName("authorizationCache");
        return shiroRealm;
    }




    /**
     * ================================shiro记住我 start=================================================================
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
//    @Bean
//    public SimpleCookie rememberMeCookie(){
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
//        // setcookie()的第七个参数
//        // 设为true后，只能通过http访问，javascript无法访问
//        // 防止xss读取Cookie
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setPath("/");
//        // <!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(2592000);
//        return simpleCookie;
//    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
//    @Bean
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));
//        return cookieRememberMeManager;
//    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//        //对应前端的checkbox的name = rememberMe
//        formAuthenticationFilter.setRememberMeParam("rememberMe");
//        return formAuthenticationFilter;
//    }
    /**
     * ===================================shiro 记住我 end===============下面是shiro session 管理 start=========================
     */

    /**
     * 配置session监听
     * @return
     */
    @Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }

    /**
     * 配置session会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(12000);
        return redisSessionDAO;
    }


    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        // setcookie()的第七个参数 //设为true后，只能通过http访问，javascript无法访问
        // 防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }


    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(ehCacheManager());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    /**
     * ===============shrio session end===============下面是shiro 的缓存(使用的是ehcache) start ===========================
     */
    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager ehCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        cacheManager.setExpire(12000);
        return cacheManager;
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");
        redisManager.setPassword("ruan330357580yuan");
        return redisManager;
    }

    /**
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{getSecurityManager()});
        return factoryBean;
    }

    /***
     * ==================================shiro ehcache缓存 end=======下面是控制shiro的并发登录=============================
     * 并发登录控制
     * @return
     */
//    @Bean
//    public ShiroConcurrentLoginFilter kickoutSessionControlFilter(){
//        ShiroConcurrentLoginFilter kickoutSessionControlFilter = new ShiroConcurrentLoginFilter();
//         //用于根据会话ID，获取会话进行踢出操作的；
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
//        kickoutSessionControlFilter.setCacheManager(ehCacheManager());
//        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
//        kickoutSessionControlFilter.setUserStatus(false);
//        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
//        kickoutSessionControlFilter.setMaxSession(1);
//        //被踢出后重定向到的地址末尾带的参数可以用作被提出的提示信息
//        kickoutSessionControlFilter.setUrl("/login?out=1");
//        return kickoutSessionControlFilter;
//     }
    /**
     * ==================================控制shiro的并发登录 end==========================================================
     */


    /**
     * ==================================退出登录跳转到登录界面 start======================================================
     * 自定义的LogoutFilter 不能交由Spring管理否则会报错
     */
    public LogoutFilter logoutFilter(){
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        return logoutFilter;
    }


}
