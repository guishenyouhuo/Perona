package com.guigui.perona.wechat.strategy.factory;

import com.google.common.collect.Maps;
import com.guigui.perona.common.utils.SpringContextUtils;
import com.guigui.perona.wechat.strategy.CommonEventProcess;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description: 事件消息处理工厂
 * @Author: guigui
 * @Date: 2019-12-11 15:33
 */
@Slf4j
public class EventMsgFactory {

    /**
     * 单例模式(静态内部类)创建serviceMap
     */
    private static class EventMsgFactoryHolder {
        static Map<String, CommonEventProcess> SERVICE_MAP = getServiceMap();
    }

    private static Map<String, CommonEventProcess> getServiceMap() {
        log.info("ContextStrategyConfig初始化处理...");
        Map<String, CommonEventProcess> serviceMap = Maps.newHashMap();
        String[] beanNames = SpringContextUtils.getContext().getBeanNamesForType(CommonEventProcess.class);
        if (beanNames != null && beanNames.length > 0) {
            for (String beanName : beanNames) {
                CommonEventProcess eventService = SpringContextUtils.getBean(beanName);
                serviceMap.put(eventService.getEventType(), eventService);
            }
        }
        log.info("CommonEventProcess提供的服务信息：{}", serviceMap.keySet());
        return serviceMap;
    }

    /**
     * 根据eventType获取对应的事件处理服务
     *
     * @param eventType
     * @return
     */
    public static CommonEventProcess getEventProcess(String eventType) {
        return EventMsgFactoryHolder.SERVICE_MAP.get(eventType);
    }
}
