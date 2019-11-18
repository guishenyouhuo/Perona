package com.guigui.perona.common.properties;

import lombok.Data;

/**
 * @Description: swagger配置
 * @Author: guigui
 * @Date: 2019-11-15 23:29
 */
@Data
public class SwaggerProperties {

    private String basePackage;
    private String title;
    private String description;
    private String author;
    private String url;
    private String email;
    private String version;
}
