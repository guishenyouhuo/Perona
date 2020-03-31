package com.guigui.perona.common.properties;

import lombok.Data;

/**
 * @Description: 项目相关配置
 * @Author: guigui
 * @Date: 2020-03-12 15:57
 */
@Data
public class ProjectProperties {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 上传路径
     */
    private String profile;

    /**
     * 获取地址开关
     */
    private boolean addressEnabled;

    /**
     * 密码重试次数
     */
    private int passwordRetries;

}
