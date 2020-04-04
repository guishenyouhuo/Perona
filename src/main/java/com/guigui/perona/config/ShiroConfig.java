package com.guigui.perona.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.guigui.perona.common.properties.PeronaProperties;
import com.guigui.perona.common.properties.ShiroProperties;
import com.guigui.perona.common.realm.AuthRealm;
import com.guigui.perona.manage.web.filter.CaptchaValidateFilter;
import com.guigui.perona.manage.web.filter.MyLogoutFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: shiro配置
 * @Author: guigui
 * @Date: 2019-11-03 14:04
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private PeronaProperties properties;

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm(hashedCredentialsMatcher()));
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器;
        securityManager.setCacheManager(ehCacheManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    public MyLogoutFilter logoutFilter() {
        MyLogoutFilter logoutFilter = new MyLogoutFilter();
        logoutFilter.setCacheManager(ehCacheManager());
        logoutFilter.setLoginUrl(properties.getShiro().getLoginUrl());
        return logoutFilter;
    }

    @Bean
    public Realm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        authRealm.setCacheManager(cacheManager());
        return authRealm;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        ShiroProperties shiro = properties.getShiro();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setDomain(shiro.getCookieDomain());
        simpleCookie.setPath(shiro.getCookiePath());
        simpleCookie.setHttpOnly(shiro.isCookieHttpOnly());
        simpleCookie.setMaxAge(shiro.getCookieMaxAge() * 24 * 60 * 60);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        ShiroProperties shiro = properties.getShiro();
        hashedCredentialsMatcher.setHashAlgorithmName(shiro.getHashAlgorithm());
        hashedCredentialsMatcher.setHashIterations(shiro.getHashTimes());
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheManager();
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new EnterpriseCacheSessionDAO();
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        ShiroProperties shiro = properties.getShiro();
        DefaultWebSessionManager session = new DefaultWebSessionManager();
        // 设置全局session超时时间（单位：毫秒）
        session.setGlobalSessionTimeout(shiro.getSessionTimeout() * 60 * 1000);
        // 去掉 JSESSIONID
        session.setSessionIdUrlRewritingEnabled(false);
        session.setDeleteInvalidSessions(true);
        session.setSessionValidationSchedulerEnabled(true);
        session.setSessionDAO(sessionDAO());
        return session;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroProperties shiro = properties.getShiro();
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        filter.setLoginUrl(shiro.getLoginUrl());
        filter.setSuccessUrl(shiro.getSuccessUrl());

        Map<String, String> filterChain = new LinkedHashMap<>();
        String[] urls = shiro.getAnonUrl().split(",");
        for (String url : urls) {
            filterChain.put(url.trim(), "anon");
        }
//        filterChain.put("/**", "user");
//        filterChain.put("/**", "anon");

        // 退出 logout地址，shiro去清除session
        filterChain.put("/manage/logout", "logout");

        // 不需要拦截的访问
        filterChain.put("/manage/login", "anon,captchaValidate");

        Map<String, Filter> filterList = new LinkedHashMap<>();
        filterList.put("captchaValidate", captchaValidateFilter());
        // 注销成功，则跳转到指定页面
        filterList.put("logout", logoutFilter());
        filter.setFilters(filterList);

        // 所有请求需要认证
        filterChain.put("/**", "user");
        filter.setFilterChainDefinitionMap(filterChain);

        return filter;
    }

    /**
     * 自定义验证码过滤器
     */
    @Bean
    public CaptchaValidateFilter captchaValidateFilter() {
        ShiroProperties shiro = properties.getShiro();
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(shiro.isCaptchaEnabled());
        captchaValidateFilter.setCaptchaType(shiro.getCaptchaType());
        return captchaValidateFilter;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
