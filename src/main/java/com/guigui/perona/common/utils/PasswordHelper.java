package com.guigui.perona.common.utils;

import com.guigui.perona.common.properties.PeronaProperties;
import com.guigui.perona.common.properties.ShiroProperties;
import com.guigui.perona.entity.UserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 密码处理
 * @Author: guigui
 * @Date: 2019-11-29 15:42
 */
@Component
public class PasswordHelper {

    //实例化RandomNumberGenerator对象，用于生成一个随机数
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Autowired
    private PeronaProperties properties;

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    //加密算法
    public void encryptPassword(UserInfo userInfo) {
        if (userInfo.getPassword() != null) {
            // 如果没有盐值就进行随机生成盐值，但是Shiro进行密码校验并不会再次生成盐值，因为是随机盐，Shiro会根据数据库中储存的盐值以及你注入的加密方式进行校验，而不是使用这个工具类进行校验的。
            //对user对象设置盐：salt；这个盐值是randomNumberGenerator生成的随机数，所以盐值并不需要我们指定
            userInfo.setSalt(randomNumberGenerator.nextBytes().toHex());

            ShiroProperties shiro = properties.getShiro();

            //调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
            String newPassword = new SimpleHash(
                    shiro.getHashAlgorithm(),
                    userInfo.getPassword(),
                    ByteSource.Util.bytes(userInfo.getSalt()),
                    shiro.getHashTimes()).toHex();
            userInfo.setPassword(newPassword);
        }
    }
}
