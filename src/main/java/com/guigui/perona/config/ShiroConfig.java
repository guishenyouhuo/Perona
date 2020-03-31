package com.guigui.perona.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.guigui.perona.common.properties.PeronaProperties;
import com.guigui.perona.common.properties.ShiroProperties;
import com.guigui.perona.common.realm.AuthRealm;
import com.guigui.perona.manage.web.filter.CaptchaValidateFilter;
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

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm(hashedCredentialsMatcher()));
        return securityManager;
    }

    @Bean
    public Realm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return authRealm;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        ShiroProperties shiro = properties.getShiro();
        SimpleCookie simpleCookie = new SimpleCookie("remember");
        simpleCookie.setMaxAge(shiro.getCookieTimeout());
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
        session.setGlobalSessionTimeout(shiro.getSessionTimeout());
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

        // 不需要拦截的访问
//        filterChain.put("/login", "anon,captchaValidate");
//        filterChain.put("/login", "anon,captchaValidate");

        filterChain.put("/manage/login", "anon,captchaValidate");

        Map<String, Filter> filterList = new LinkedHashMap<>();
        filterList.put("captchaValidate", captchaValidateFilter());
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
