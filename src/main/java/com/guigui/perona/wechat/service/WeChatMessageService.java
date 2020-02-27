package com.guigui.perona.wechat.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 消息处理服务接口
 * @Author: guigui
 * @Date: 2019-12-11 11:38
 */
public interface WeChatMessageService {
    // 新微信消息请求处理
    String newMessageRequest(HttpServletRequest request) throws Exception;
}
