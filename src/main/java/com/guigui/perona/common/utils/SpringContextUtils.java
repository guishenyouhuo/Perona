package com.guigui.perona.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: spring context获取工具
 * @Author: guigui
 * @Date: 2019-12-11 15:40
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    private static ApplicationContext context = null;

    public static Object getBean(String beanId) {
        return context.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        logger.info("初始化 SpringContext");
        SpringContextUtils.context = applicationContext;
    }

    public static void setContext(ApplicationContext ctx) {
        SpringContextUtils.context = ctx;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
