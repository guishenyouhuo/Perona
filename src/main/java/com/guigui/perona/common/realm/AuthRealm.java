package com.guigui.perona.common.realm;

import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IMenuInfoService;
import com.guigui.perona.service.IRoleInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @Description: 认证处理
 * @Author: guigui
 * @Date: 2019-11-03 14:17
 */
public class AuthRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AuthRealm.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private IMenuInfoService menuInfoService;

    @Autowired
    private IRoleInfoService roleInfoService;

    /**
     * 权限校验
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = ShiroUtils.getUserInfo();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 角色列表
        Set<String> roles = roleInfoService.selectRoleKeysByUserId(userInfo.getId());
        // 角色加入AuthorizationInfo认证对象
        info.setRoles(roles);
        // 管理员拥有所有权限
        if (userInfo.isSuperAdmin()) {
            info.addStringPermission("*:*:*");
        } else {
            // 功能列表
            Set<String> menus = menuInfoService.selectPermsByUserId(userInfo.getId());
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 身份校验
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        UserInfo userInfo = null;
        try {
            userInfo = loginService.login(username, password);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getSalt()), getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
