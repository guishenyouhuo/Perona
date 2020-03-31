package com.guigui.perona.service;

import com.guigui.perona.entity.UserInfo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface IUserInfoService {

    /**
     * 查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    UserInfo selectUserInfoById(Long id);

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户集合
     */
    List<UserInfo> selectUserInfoList(UserInfo userInfo);

    /**
     * 新增用户
     *
     * @param userInfo 用户
     * @return 结果
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户
     *
     * @param userInfo 用户
     * @return 结果
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 更新用户记录
     * @param userInfo 用户
     * @return 结果
     */
    int updateUserRecord(UserInfo userInfo);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserInfoByIds(String ids);

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     * @return 结果
     */
    int deleteUserInfoById(Long id);

    /**
     * 根据username查询用户数据
     *
     * @param username
     * @return
     */
    UserInfo findByName(String username);

    /**
     * 根据手机号查询用户数据
     *
     * @param mobileNo
     * @return
     */
    UserInfo findByMobileNo(String mobileNo);

    /**
     * 根据email查询用户数据
     *
     * @param email
     * @return
     */
    UserInfo findByEmail(String email);

    /**
     * 新增
     *
     * @param userInfo
     */
    void add(UserInfo userInfo);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param userInfo 用户信息
     * @return 用户信息集合信息
     */
    List<UserInfo> selectAllocatedList(UserInfo userInfo);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param userInfo 用户信息
     * @return 用户信息集合信息
     */
    List<UserInfo> selectUnAllocatedList(UserInfo userInfo);

    /**
     * 校验用户是否唯一
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    String checkUserUnique(UserInfo userInfo);

    /**
     * 校验用户是否允许操作
     *
     * @param userInfo 用户信息
     */
    void checkUserAllowed(UserInfo userInfo);

    /**
     * 修改用户密码
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    int resetUserPwd(UserInfo userInfo);

    /**
     * 用户状态修改
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    int changeStatus(UserInfo userInfo);

}
