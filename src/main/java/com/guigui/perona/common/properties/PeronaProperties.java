package com.guigui.perona.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 系统配置
 * @Author: guigui
 * @Date: 2019-11-03 14:07
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "perona")
public class PeronaProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private SwaggerProperties swagger = new SwaggerProperties();

    private GenProperties gen = new GenProperties();

    private ProjectProperties project = new ProjectProperties();

}
