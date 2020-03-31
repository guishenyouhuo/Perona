package com.guigui.perona.common.utils;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.ShiroConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.manager.AsyncManager;
import com.guigui.perona.common.manager.factory.AsyncTaskFactory;
import com.guigui.perona.common.properties.PeronaProperties;
import com.guigui.perona.common.properties.ShiroProperties;
import com.guigui.perona.entity.UserInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    //加密算法
    public void encryptPassword(UserInfo userInfo) {
        if (userInfo.getPassword() != null) {
            // 如果没有盐值就进行随机生成盐值，但是Shiro进行密码校验并不会再次生成盐值，因为是随机盐，Shiro会根据数据库中储存的盐值以及你注入的加密方式进行校验，而不是使用这个工具类进行校验的。
            // 对user对象设置盐：salt；这个盐值是randomNumberGenerator生成的随机数，所以盐值并不需要我们指定
            userInfo.setSalt(randomNumberGenerator.nextBytes().toHex());
            userInfo.setPassword(encryptPassword(userInfo.getPassword(), userInfo.getSalt()));
        }
    }

    public String encryptPassword(String password, String salt) {
        ShiroProperties shiro = properties.getShiro();
        // 调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
        return new SimpleHash(shiro.getHashAlgorithm(), password, ByteSource.Util.bytes(salt), shiro.getHashTimes()).toHex();
    }

    public void validate(UserInfo userInfo, String password) {
        String userName = userInfo.getUsername();

        AtomicInteger retryCount = loginRecordCache.get(userName);
        int maxRetryCount = properties.getProject().getPasswordRetries();

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(userName, retryCount);
        }
        if (retryCount.incrementAndGet() > maxRetryCount) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(userName, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new GlobalException("密码错误次数超过：" + maxRetryCount + "次!");
        }

        if (StringUtils.equals(userInfo.getPassword(), encryptPassword(password, userInfo.getSalt()))) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(userName, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            loginRecordCache.put(userName, retryCount);
            throw new GlobalException("密码不匹配！");
        }
        clearLoginRecordCache(userName);
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

}
