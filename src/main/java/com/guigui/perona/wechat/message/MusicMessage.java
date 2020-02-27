package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 音乐类型消息
 * @Author: guigui
 * @Date: 2019-12-10 20:33
 */
@Data
@XStreamAlias("MusicMessage")
public class MusicMessage extends BaseMessage {

    /**
     * 音乐
     */
    @XStreamAlias("Music")
    private Music music;
}
