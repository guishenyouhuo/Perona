package com.guigui.perona.service;

import com.guigui.perona.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    UserInfo findByName(String username);
}
