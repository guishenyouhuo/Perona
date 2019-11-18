package com.guigui.perona.common.properties;

import lombok.Data;

/**
 * @Description: 云服务配置
 * @Author: guigui
 * @Date: 2019-11-15 23:27
 */
@Data
public class CloudProperties {
    /**
     * AccessKey
     */
    private String ak;

    /**
     * SecretKey
     */
    private String sk;

    /**
     * BucketName
     */
    private String bn;

    /**
     * 外链
     */
    private String url;
}
