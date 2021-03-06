package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guigui.perona.common.utils.PasswordHelper;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.mapper.UserInfoMapper;
import com.guigui.perona.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public UserInfo findByName(String username) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username);
        List<UserInfo> list = userInfoMapper.selectList(queryWrapper);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    @Transactional
    public void add(UserInfo userInfo) {
        passwordHelper.encryptPassword(userInfo); //加密
        userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfo userInfo) {
        if (userInfo.getPassword() != null && !"".equals(userInfo.getPassword())) {
            passwordHelper.encryptPassword(userInfo); //加密
        } else {
            userInfo.setPassword(null);
        }
        userInfoMapper.updateById(userInfo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userInfoMapper.deleteById(id);
    }
}
