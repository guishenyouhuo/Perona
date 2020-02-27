package com.guigui.perona.wechat.template;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: guigui
 * @Date: 2019-12-11 22:30
 */
public class SendMessage {
    public static String sendTemplateMsg(String token, Template template) throws Exception {

        boolean flag = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

        String jsonResult = HttpUtil.httpUrlConnect(requestUrl, template.toJSON(), "POST");
//        if (jsonResult != null) {
//            int errorCode = jsonResult.getInt("errcode");
//            String errorMessage = jsonResult.getString("errmsg");
//            if (errorCode == 0) {
//                flag = true;
//            } else {
//                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
//                flag = false;
//            }
//        }
        return jsonResult;
    }

    public static void main(String[] args) throws Exception {
        //先给模板中设置参数
        Template tem = new Template();
        tem.setTemplateId("MTmkP84lz0Yng0iT28KwZ7yz_yzsz0lf5_LzHZucxW0");  //模板id
        tem.setTopColor("#00DD00");
        tem.setToUser("oyHmO1Nh1KVkBy_ncAPo1HFkIwng");//得到用户的openid
        tem.setUrl("");
        List<TemplateParam> paras = new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", "当前事项提醒:", "#FF3333"));
        paras.add(new TemplateParam("eventTime", new Date().toString(), "#0044BB"));
        paras.add(new TemplateParam("eventMsg", "该吃药啦！", "#0044BB"));
        paras.add(new TemplateParam("remark", "吃药时间到了，赶紧吃药！", "#AAAAAA"));
        tem.setTemplateParamList(paras);
        //将Accesstoken拿到
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String APPID = "wx713a694094af0104";
        String SECRET = "d54f645ac9d906919928fa17b02d176e";
        url = url.replace("APPID", APPID);
        url = url.replace("APPSECRET", SECRET);
        String content = HttpUtil.httpUrlConnect(url, null, "GET");
        Map<String, Object> map = HttpUtil.getAccessTokenByJsonStr(content);
        String accessToken = (String) map.get("access_token");
        String result = SendMessage.sendTemplateMsg(accessToken, tem);
        System.out.println("发送成功" + result);

    }

}
