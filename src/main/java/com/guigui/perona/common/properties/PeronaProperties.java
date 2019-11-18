package com.guigui.perona.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description: 系统配置
 * @Author: guigui
 * @Date: 2019-11-03 14:07
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:perona.properties"})
@ConfigurationProperties(prefix = "perona")
public class PeronaProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private SwaggerProperties swagger = new SwaggerProperties();

    private CloudProperties cloud = new CloudProperties();

}
