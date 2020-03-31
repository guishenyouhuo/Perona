package com.guigui.perona.manage.web.service;

import com.guigui.perona.service.IParamConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * thymeleaf特性 html直接调用 实现参数管理
 *
 * @author guigui
 */
@Service("config")
public class ConfigService {
    @Autowired
    private IParamConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.selectParamConfigByKey(configKey);
    }

}
