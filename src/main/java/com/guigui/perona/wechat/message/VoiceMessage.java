package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 语音消息
 * @Author: guigui
 * @Date: 2019-12-10 20:30
 */
@Data
@XStreamAlias("VoiceMessage")
public class VoiceMessage extends BaseMessage {
}
