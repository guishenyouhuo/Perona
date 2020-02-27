package com.guigui.perona.wechat.constants;

/**
 * @Description: 微信相关常量
 * @Author: guigui
 * @Date: 2019-12-11 11:32
 */
public final class WeChatConstants {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 校验签名，加密类型
     */
    public static final String ENCRYPT_NAME = "SHA-1";


    /**
     * 发送方用户KEY
     */
    public static final String FROM_USER_KEY = "FromUserName";

    /**
     * 接收方用户KEY
     */
    public static final String TO_USER_KEY = "ToUserName";

    /**
     * 消息类型KEY
     */
    public static final String MSG_TYPE_KEY = "MsgType";

    /**
     * 消息内容KEY
     */
    public static final String CONTENT_KEY = "Content";

    /**
     * 事件类型KEY
     */
    public static final String EVENT_KEY = "Event";

    /**
     * 事件KEY
     */
    public static final String EVENT_KEY_KEY = "EventKey";

}
