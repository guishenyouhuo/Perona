package com.guigui.perona.service;

import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.entity.UserRole;

import java.util.List;
import java.util.Set;

/**
 * 角色信息Service接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface IRoleInfoService {
    /**
     * 查询角色信息
     *
     * @param roleId 角色信息ID
     * @return 角色信息
     */
    RoleInfo selectRoleInfoById(Long roleId);

    /**
     * 查询角色信息列表
     *
     * @param roleInfo 角色信息
     * @return 角色信息集合
     */
    List<RoleInfo> selectRoleInfoList(RoleInfo roleInfo);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRoleKeysByUserId(Long userId);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    String checkRoleNameUnique(RoleInfo roleInfo);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    String checkRoleKeyUnique(RoleInfo roleInfo);

    /**
     * 新增角色信息
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    int insertRoleInfo(RoleInfo roleInfo);

    /**
     * 修改角色信息
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    int updateRoleInfo(RoleInfo roleInfo);

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleInfoByIds(String ids);

    /**
     * 删除角色信息信息
     *
     * @param roleId 角色信息ID
     * @return 结果
     */
    int deleteRoleInfoById(Long roleId);

    /**
     * 校验角色是否允许操作
     *
     * @param roleInfo 角色信息
     */
    void checkRoleAllowed(RoleInfo roleInfo);

    /**
     * 角色状态修改
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    int changeStatus(RoleInfo roleInfo);

    /**
     * 修改数据权限信息
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    int authDataScope(RoleInfo roleInfo);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteAuthUser(UserRole userRole);

    /**
     * 批量授权选择的用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUserRoles(Long roleId, String userIds);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, String userIds);

}
