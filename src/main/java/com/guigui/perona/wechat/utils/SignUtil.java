package com.guigui.perona.wechat.utils;

import com.guigui.perona.wechat.constants.WeChatConstants;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @Description: 请求校验工具类
 * @Author: guigui
 * @Date: 2019-12-05 17:36
 */
public class SignUtil {

    // 与接口配置信息中的 Token 要一致
    private static String token = "ghostperonawechat";

    /**
     * 校验签名
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 布尔值
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) throws Exception {
        String checktext = null;
        if (null != signature) {
            //对ToKen,timestamp,nonce 按字典排序
            String[] paramArr = new String[]{token, timestamp, nonce};
            Arrays.sort(paramArr);
            //将排序后的结果拼成一个字符串
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            MessageDigest md = MessageDigest.getInstance(WeChatConstants.ENCRYPT_NAME);
            //对接后的字符串进行sha1加密
            byte[] digest = md.digest(content.getBytes());
            checktext = byteToStr(digest);
        }
        //将加密后的字符串与signature进行对比
        return checktext != null && checktext.equals(signature.toUpperCase());
    }

    /**
     * 将字节数组转化为16进制字符串
     *
     * @param byteArrays 字符数组
     * @return 字符串
     */
    private static String byteToStr(byte[] byteArrays) {
        StringBuilder str = new StringBuilder();
        for (byte byteArray : byteArrays) {
            str.append(byteToHexStr(byteArray));
        }
        return str.toString();
    }

    /**
     * 将字节转化为十六进制字符串
     *
     * @param myByte 字节
     * @return 字符串
     */
    private static String byteToHexStr(byte myByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tampArr = new char[2];
        tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = Digit[myByte & 0X0F];
        return new String(tampArr);
    }
}
