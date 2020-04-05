package com.guigui.perona.service.impl;

import com.guigui.perona.common.aspect.annotation.DataScope;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.*;
import com.guigui.perona.common.utils.text.Convert;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.entity.UserRole;
import com.guigui.perona.mapper.RoleInfoMapper;
import com.guigui.perona.mapper.UserInfoMapper;
import com.guigui.perona.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public UserInfo findByName(String username) {
        return userInfoMapper.selectUserInfoByUserName(username);
    }

    @Override
    public UserInfo findByMobileNo(String mobileNo) {
        return userInfoMapper.selectUserInfoByPhoneNumber(mobileNo);
    }

    @Override
    public UserInfo findByEmail(String email) {
        return userInfoMapper.selectUserInfoByEmail(email);
    }

    @Override
    @Transactional
    public void add(UserInfo userInfo) {
        // 密码加密
        passwordHelper.encryptPassword(userInfo);
        userInfoMapper.insertUserInfo(userInfo);
    }

    @Override
    public List<UserInfo> selectAllocatedList(UserInfo userInfo) {
        return userInfoMapper.selectAllocatedList(userInfo);
    }

    @Override
    public List<UserInfo> selectUnAllocatedList(UserInfo userInfo) {
        return userInfoMapper.selectUnAllocatedList(userInfo);
    }

    @Override
    public String checkUserUnique(UserInfo userInfo) {
        long userId = userInfo.getId() == null ? -1L : userInfo.getId();
        UserInfo existUser = userInfoMapper.checkUserUnique(userInfo);
        if (existUser != null && existUser.getId() != userId) {
            return UserConstants.USER_NOT_UNIQUE;
        }
        return UserConstants.USER_UNIQUE;
    }

    @Override
    public UserInfo selectUserInfoById(Long id) {
        return userInfoMapper.selectUserInfoById(id);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<UserInfo> selectUserInfoList(UserInfo userInfo) {
        return userInfoMapper.selectUserInfoList(userInfo);
    }

    @Override
    @Transactional
    public int insertUserInfo(UserInfo userInfo) {
        // 密码加密
        if (StringUtils.isNotEmpty(userInfo.getPassword())) {
            passwordHelper.encryptPassword(userInfo);
        } else {
            userInfo.setPassword(null);
        }
        userInfo.setCreateBy(ShiroUtils.getLoginName());
        userInfo.setCreateTime(DateUtils.getNowDate());
        int result = userInfoMapper.insertUserInfo(userInfo);
        // 新增用户与角色管理
        insertUserRole(userInfo);
        return result;
    }

    @Override
    @Transactional
    public int updateUserInfo(UserInfo userInfo) {
        // 密码加密
        if (StringUtils.isNotEmpty(userInfo.getPassword())) {
            passwordHelper.encryptPassword(userInfo);
        } else {
            userInfo.setPassword(null);
        }
        userInfo.setUpdateBy(ShiroUtils.getLoginName());
        userInfo.setUpdateTime(DateUtils.getNowDate());
        int result = userInfoMapper.updateUserInfo(userInfo);
        // 删除用户与角色关联
        userInfoMapper.deleteUserRoleByUserId(userInfo.getId());
        // 新增用户与角色管理
        insertUserRole(userInfo);
        return result;
    }

    @Override
    public int updateUserRecord(UserInfo userInfo) {
        return userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public int deleteUserInfoByIds(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            checkUserAllowed(new UserInfo(userId));
        }
        return userInfoMapper.deleteUserInfoByIds(userIds);
    }

    @Override
    @Transactional
    public int deleteUserInfoById(Long id) {
        return userInfoMapper.deleteUserInfoById(id);
    }

    /**
     * 新增用户角色信息
     *
     * @param userInfo 用户对象
     */
    private void insertUserRole(UserInfo userInfo) {
        Long[] roles = userInfo.getRoleIds();
        if (roles != null) {
            // 新增用户与角色管理
            List<UserRole> list = new ArrayList<>();
            for (Long roleId : userInfo.getRoleIds()) {
                UserRole ur = new UserRole();
                ur.setUserId(userInfo.getId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userInfoMapper.batchInsertUserRole(list);
            }
        }
    }

    /**
     * 校验用户是否允许操作
     *
     * @param userInfo 用户信息
     */
    public void checkUserAllowed(UserInfo userInfo) {
        if (userInfo.getId() != null && userInfo.isSuperAdmin()) {
            throw new GlobalException("不允许操作超级管理员用户");
        }
    }

    @Override
    public int resetUserPwd(UserInfo userInfo) {
        // 密码加密
        if (StringUtils.isNotEmpty(userInfo.getPassword())) {
            passwordHelper.encryptPassword(userInfo);
        } else {
            userInfo.setPassword(null);
        }
        userInfo.setUpdateBy(ShiroUtils.getLoginName());
        userInfo.setUpdateTime(DateUtils.getNowDate());
        return userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public int changeStatus(UserInfo userInfo) {
        return userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<RoleInfo> roleList = roleInfoMapper.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (RoleInfo roleInfo : roleList) {
            idsStr.append(roleInfo.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String checkLoginNameUnique(UserInfo userInfo) {
        long userId = userInfo.getId() == null ? -1L : userInfo.getId();
        UserInfo info = findByName(userInfo.getUsername());
        if (info != null && info.getId() != userId) {
            return UserConstants.USER_NOT_UNIQUE;
        }
        return UserConstants.USER_UNIQUE;
    }

    @Override
    public String checkPhoneUnique(UserInfo userInfo) {
        long userId = userInfo.getId() == null ? -1L : userInfo.getId();
        UserInfo info = findByMobileNo(userInfo.getPhoneNumber());
        if (info != null && info.getId() != userId) {
            return UserConstants.USER_NOT_UNIQUE;
        }
        return UserConstants.USER_UNIQUE;
    }

    @Override
    public String checkEmailUnique(UserInfo userInfo) {
        long userId = userInfo.getId() == null ? -1L : userInfo.getId();
        UserInfo info = findByEmail(userInfo.getEmail());
        if (info != null && info.getId() != userId) {
            return UserConstants.USER_NOT_UNIQUE;
        }
        return UserConstants.USER_UNIQUE;
    }
}
