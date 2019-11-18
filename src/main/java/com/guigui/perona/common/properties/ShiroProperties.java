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
    private int cookieTimeout;
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
}
