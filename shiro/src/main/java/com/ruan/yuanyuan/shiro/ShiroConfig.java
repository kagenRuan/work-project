package com.ruan.yuanyuan.shiro;

import com.ruan.yuanyuan.config.redis.MySessionManager;
import com.ruan.yuanyuan.filter.MyauthFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

/**
 * @ClassName: ShiroCOnfig
 * @author: ruanyuanyuan
 * @date: 2020/7/5 15:25
 * @version: 1.0
 * @description: Shiro 权限配置类
 **/
@Configuration
public class ShiroConfig {

    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


    //自定义realm
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入自定义的realm;
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("authc", new MyauthFilter());
        shiroFilterFactoryBean.setFilters(filters);

        shiroFilterFactoryBean.setUnauthorizedUrl("http://127.0.0.1:63343/page/404.html");
        shiroFilterFactoryBean.setLoginUrl("http://127.0.0.1:63343/page/login.html");
        filterChainDefinitionMap.put("/api/login/main", "anon");//登录
        filterChainDefinitionMap.put("/api/login/logout", "anon");//退出

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //加密算法
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数
        return hashedCredentialsMatcher;
    }

    /**
     * 会话管理
     **/
    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false); //取消登陆跳转URL后面的jsessionid参数
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setGlobalSessionTimeout(3600000);//不过期 可以设置session的刷新周期
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 使用的是shiro-redis开源插件 缓存依赖
     **/
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");
        redisManager.setTimeout(3600);
        redisManager.setPassword("123456");
        return redisManager;
    }

    /**
     * 使用的是shiro-redis开源插件 session持久化
     **/
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    /**
     * 缓存管理
     **/
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setPrincipalIdFieldName("id");//在用户授权的时候可能会报错，这里填入用户的主键
        return redisCacheManager;
    }
    /**
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     　　* 开启shiro aop注解支持.
     　　* 使用代理方式;所以需要开启代码支持;
     　　*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


//===============================================




    /**
     * 将创建的shiro Filter交给Spring容器管理
     * @return FilterRegistrationBean
     */
//    @Bean
//    public FilterRegistrationBean  filterRegistrationBean(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//        delegatingFilterProxy.setTargetFilterLifecycle(true);
//        delegatingFilterProxy.setTargetBeanName("shiroFilterFactoryBean");
//        filterRegistrationBean.setFilter(delegatingFilterProxy);
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.ERROR,DispatcherType.REQUEST,DispatcherType.INCLUDE);
//        return filterRegistrationBean;
//    }

    /**
     * 创建Realm
     */
//    @Bean
//    public ShiroRealm getRealm() {
//        ShiroRealm shiroRealm = new ShiroRealm();
//        shiroRealm.setCachingEnabled(true);
//        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
//        shiroRealm.setAuthenticationCachingEnabled(true);
//        shiroRealm.setAuthenticationCacheName("authenticationCache");
//        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
//        shiroRealm.setAuthorizationCachingEnabled(true);
//        shiroRealm.setAuthorizationCacheName("authorizationCache");
//        return shiroRealm;
//    }
//
//    /**
//     * 创建DefaultWebSecurityManager 第一步
//     */
//    @Bean
//    public SecurityManager defaultWebSecurityManager(ShiroRealm shiroRealm) {
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        //这里需要关联Realm
//        defaultWebSecurityManager.setRealm(shiroRealm);
//        return defaultWebSecurityManager;
//    }
//
//    /**
//     * 创建ShiroFilterFbean 过滤器
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("/api/login/main");//设置登录请求URL
//        shiroFilterFactoryBean.setUnauthorizedUrl("/api/unauthorized/return401");//设置未授权请求跳转URL
//
//
//        /**CORSFilter
//         * 配置访问权限
//         * shiro过滤器：可以实现拦截相关权限
//         *      常用过滤器：
//         *             anon:无需认证可以直接访问
//         *             authc:必须认证才能访问
//         */
//        Map<String, String> map = new LinkedHashMap<>();
//        //其他接口都需要进行验证授权
//        map.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//
//        return shiroFilterFactoryBean;
//    }
//
//
//    /**
//     * shiro注解支持
//     * @param securityManager
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }

}
