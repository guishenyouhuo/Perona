package com.guigui.perona.common.properties;

import lombok.Data;

/**
 * @Description: shiro相关配置
 * @Author: guigui
 * @Date: 2019-11-03 14:08
 */
@Data
public class ShiroProperties {
    private long sessionTimeout;
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
    private int hashTimes;
    private String hashAlgorithm;
    private boolean captchaEnabled;
    private String captchaType;
    // cookie配置
    private String cookieDomain;
    private String cookiePath;
    private boolean cookieHttpOnly;
    private int cookieMaxAge;
}
