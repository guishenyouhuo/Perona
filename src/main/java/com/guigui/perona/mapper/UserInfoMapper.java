package com.guigui.perona.mapper;

import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author guigui
 * @date 2020-03-11
 */
public interface UserInfoMapper {
    /**
     * 根据主键查询用户
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
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    UserInfo selectUserInfoByUserName(String userName);

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    UserInfo selectUserInfoByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    UserInfo selectUserInfoByEmail(String email);

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
     * 删除用户
     *
     * @param id 用户ID
     * @return 结果
     */
    int deleteUserInfoById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserInfoByIds(Long[] ids);

    /**
     * 根据条件分页查询未已配用户角色列表
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
     * 检查用户信息是否唯一
     *
     * @param userInfo 检查参数
     * @return 用户信息
     */
    UserInfo checkUserUnique(UserInfo userInfo);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteUserRoleInfo(UserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchInsertUserRole(List<UserRole> userRoleList);

}
