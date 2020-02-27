package com.guigui.perona.wechat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.wechat.service.WeChatMessageService;
import com.guigui.perona.wechat.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 微信消息接收controller
 * @Author: guigui
 * @Date: 2019-12-11 17:17
 */
@RestController
@Slf4j
@RequestMapping("/weChat/index")
public class WeChatIndexController {

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private WeChatMessageService weChatMessageService;

    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) {

        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echoStr = request.getParameter("echostr");
        // 获取PrintWriter
        PrintWriter out = getPrintWriter(response);
        // 如果为空，直接返回
        if (out == null) {
            log.warn("PrintWriter获取出现异常，无法继续处理下去~~");
            return;
        }
        log.info("开始进行校验：signature：{}, timestamp: {}, nonce: {}, echoStr: {}", signature, timestamp, nonce, echoStr);
        try {
            // 通过检验signature对请求进行校验，若校验成功则原样返回echoStr，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.info("请求校验结果成功！echoStr：{}", echoStr);
                out.print(echoStr);
            }
        } catch (Exception e) {
            log.error("校验出现异常！signature：{}, timestamp: {}, nonce: {}", signature, timestamp, nonce, e);
        } finally {
            out.close();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(CommonConstants.UTF8);
        // 响应消息, 获取PrintWriter
        PrintWriter out = getPrintWriter(response);
        // 如果为空，直接返回
        if (out == null) {
            log.warn("PrintWriter获取出现异常，无法继续处理下去~");
            return;
        }
        log.info("开始对微信消息进行处理...");
        try {
            request.setCharacterEncoding(CommonConstants.UTF8);
            // 调用核心业务类接收消息、处理消息
            String respMessage = weChatMessageService.newMessageRequest(request);
            log.info("微信消息处理结果：respMessage: {}", MAPPER.writeValueAsString(respMessage));
            out.print(respMessage);
        } catch (Exception e) {
            log.error("微信消息处理出现异常！", e);
        } finally {
            out.close();
        }
    }

    // 从response中获取PrintWriter
    private PrintWriter getPrintWriter(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error("回写对象PrintWriter获取出现异常！", e);
        }
        return out;
    }
}
