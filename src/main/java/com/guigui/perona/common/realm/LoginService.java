package com.guigui.perona.common.realm;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.ShiroConstants;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.enums.UserStatus;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.manager.AsyncManager;
import com.guigui.perona.common.manager.factory.AsyncTaskFactory;
import com.guigui.perona.common.utils.MessageUtils;
import com.guigui.perona.common.utils.PasswordHelper;
import com.guigui.perona.common.utils.ServletUtils;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 *
 * @author guigui
 */
@Component
public class LoginService {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 登录
     */
    public UserInfo login(String username, String password) {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new GlobalException("验证码校验失败！");
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new GlobalException("用户名或密码为空！");
        }
        // 查询用户信息
        UserInfo userInfo = userInfoService.findByName(username);

        if (userInfo == null && maybeMobilePhoneNumber(username)) {
            userInfo = userInfoService.findByMobileNo(username);
        }

        if (userInfo == null && maybeEmail(username)) {
            userInfo = userInfoService.findByEmail(username);
        }

        if (userInfo == null) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new GlobalException("用户不存在！");
        }

        if (UserStatus.DELETED.getCode().equals(userInfo.getDelFlag())) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new GlobalException("用户已经被删除！");
        }

        if (UserStatus.DISABLE.getCode().equals(userInfo.getStatus())) {
            AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.blocked", userInfo.getRemark())));
            throw new GlobalException("用户已经被封禁！");
        }
        passwordHelper.validate(userInfo, password);
        // 登陆成功
        AsyncManager.async().execute(AsyncTaskFactory.recordLoginLog(username, CommonConstants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        AsyncManager.async().execute(AsyncTaskFactory.recordUserInfo(userInfo));
        return userInfo;
    }

    private boolean maybeEmail(String username) {
        return username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

}
