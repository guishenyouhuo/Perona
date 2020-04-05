package com.guigui.perona.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * spring context获取工具
 *
 * @author guigui
 */
@Component
public class SpringContextUtils implements BeanFactoryPostProcessor {

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    private static ConfigurableListableBeanFactory beanFactory = null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) throws BeansException {
        return (T) beanFactory.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return beanFactory.getBean(clazz);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("初始化 SpringContext");
        SpringContextUtils.beanFactory = beanFactory;
    }

    public static ConfigurableListableBeanFactory getContext() {
        return SpringContextUtils.beanFactory;
    }

}
