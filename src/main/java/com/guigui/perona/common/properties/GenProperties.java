package com.guigui.perona.common.properties;

import lombok.Data;

/**
 * @Description: 代码生成器相关配置
 * @Author: guigui
 * @Date: 2020-03-07 23:40
 */
@Data
public class GenProperties {

    /**
     * 作者
     */
    private String author;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 自动去除表前缀，默认是true
     */
    private boolean autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix;

}
