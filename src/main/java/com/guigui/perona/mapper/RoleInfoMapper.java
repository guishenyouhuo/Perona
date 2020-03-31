package com.guigui.perona.mapper;

import com.guigui.perona.entity.RoleDept;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.entity.RoleMenu;

import java.util.List;

/**
 * 角色信息Mapper接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface RoleInfoMapper {
    /**
     * 根据主键查询角色信息
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
     * @return 角色列表
     */
    List<RoleInfo> selectRolesByUserId(Long userId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuByMenuId(Long menuId);

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
     * 删除角色信息
     *
     * @param roleId 角色信息ID
     * @return 结果
     */
    int deleteRoleInfoById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleInfoByIds(String[] roleIds);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    RoleInfo checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    RoleInfo checkRoleKeyUnique(String roleKey);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量新增角色菜单关联信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<RoleMenu> roleMenuList);

    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量新增角色部门关联信息
     *
     * @param roleDeptList 角色菜单列表
     * @return 结果
     */
    int batchRoleDept(List<RoleDept> roleDeptList);
}
