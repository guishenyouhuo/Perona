package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 音乐类型
 * @Author: guigui
 * @Date: 2019-12-10 20:31
 */
@Data
@XStreamAlias("Music")
public class Music {
    /**
     * 音乐名称
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 音乐描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 音乐链接
     */
    @XStreamAlias("MusicUrl")
    private String musicUrl;

    /**
     * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     */
    @XStreamAlias("HQMusicUrl")
    private String hQMusicUrl;
}
