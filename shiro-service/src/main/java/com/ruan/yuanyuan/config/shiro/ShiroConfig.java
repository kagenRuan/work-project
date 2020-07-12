package com.ruan.yuanyuan.config.shiro;

import com.ruan.yuanyuan.config.session.MySessionManager;
import com.ruan.yuanyuan.config.shiro.filter.CaptchaFormAuthenticationFilter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import org.apache.shiro.mgt.SecurityManager;

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

    /**
     * 创建Realm
     */
    @Bean
    public ShiroRealm getRealm() {
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
     * 创建DefaultWebSecurityManager 第一步
     */
    @Bean
    public SecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //这里需要关联Realm
        defaultWebSecurityManager.setRealm(getRealm());
        //配置记住我
//        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        //配置 ehcache缓存管理器
        defaultWebSecurityManager.setCacheManager(ehCacheManager());
        //配置自定义session管理，使用ehcache
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    /**
     * 创建ShiroFilterFbean 过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/return401");

        /**
         * 自定义Filter
         */
        Map<String, Filter> filter = new LinkedHashMap<>();
        filter.put("authc",new CaptchaFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filter);


        /**
         * 配置访问权限
         * shiro过滤器：可以实现拦截相关权限
         *      常用过滤器：
         *             anon:无需认证可以直接访问
         *             authc:必须认证才能访问
         */
        Map<String, String> map = new LinkedHashMap<>();
        //登录接口不需要拦截,可以直接访问
        map.put("/api/login/main","anon");
        map.put("/api/login/logout","anon");
        map.put("/api/menu/list","anon");
        //验证码接口不需要拦截，可以直接访问
        map.put("/api/login/genCaptcha","anon");
        //其他接口都需要进行验证授权
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置会话管理器，设定会话超时及保存
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(6000000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }


    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        // setcookie()的第七个参数
        // 设为true后，只能通过http访问，javascript无法访问
        // 防止xss读取Cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        // <!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * AOP方法级权限检查
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 保证Shiro内部ifecycle函数Bean执行
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * shiro注解支持
     * @param securityManager
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager ehCacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        cacheManager.setExpire(6000000);
        return cacheManager;
    }
    /**
     * Redis 管理器
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(genericObjectPoolConfig,"127.0.0.1",6379,30000,"123456");
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        //sessionkey的前缀
        redisSessionDAO.setKeyPrefix("shiro_");
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(6000000);
        return redisSessionDAO;
    }


}
