package com.guigui.perona.service.impl;

import java.util.*;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.common.utils.StringUtils;
import com.guigui.perona.entity.RoleDept;
import com.guigui.perona.entity.RoleMenu;
import com.guigui.perona.entity.UserRole;
import com.guigui.perona.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.RoleInfoMapper;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.service.IRoleInfoService;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色信息Service业务层处理
 *
 * @author guigui
 * @date 2020-03-13
 */
@Service
public class RoleInfoServiceImpl implements IRoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 查询角色信息
     *
     * @param roleId 角色信息ID
     * @return 角色信息
     */
    @Override
    public RoleInfo selectRoleInfoById(Long roleId) {
        return roleInfoMapper.selectRoleInfoById(roleId);
    }

    /**
     * 查询角色信息列表
     *
     * @param roleInfo 角色信息
     * @return 角色信息
     */
    @Override
    public List<RoleInfo> selectRoleInfoList(RoleInfo roleInfo) {
        return roleInfoMapper.selectRoleInfoList(roleInfo);
    }

    @Override
    public Set<String> selectRoleKeysByUserId(Long userId) {
        List<RoleInfo> roles = roleInfoMapper.selectRolesByUserId(userId);
        Set<String> roleKeySet = new HashSet<>();
        for (RoleInfo roleInfo : roles) {
            String roleKey = roleInfo.getRoleKey();
            if (StringUtils.isNotEmpty(roleKey)) {
                roleKeySet.addAll(Arrays.asList(roleKey.trim().split(",")));
            }
        }
        return roleKeySet;
    }

    @Override
    public String checkRoleNameUnique(RoleInfo roleInfo) {
        long roleId = roleInfo.getRoleId() == null ? -1L : roleInfo.getRoleId();
        RoleInfo info = roleInfoMapper.checkRoleNameUnique(roleInfo.getRoleName());
        if (info != null && info.getRoleId() != roleId) {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(RoleInfo roleInfo) {
        long roleId = roleInfo.getRoleId() == null ? -1L : roleInfo.getRoleId();
        RoleInfo info = roleInfoMapper.checkRoleKeyUnique(roleInfo.getRoleKey());
        if (info != null && info.getRoleId() != roleId) {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    /**
     * 新增角色信息
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRoleInfo(RoleInfo roleInfo) {
        roleInfo.setCreateBy(ShiroUtils.getLoginName());
        roleInfo.setCreateTime(DateUtils.getNowDate());
        roleInfoMapper.insertRoleInfo(roleInfo);
        ShiroUtils.clearCachedAuthorizationInfo();
        return insertRoleMenu(roleInfo);
    }

    /**
     * 修改角色信息
     *
     * @param roleInfo 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRoleInfo(RoleInfo roleInfo) {
        roleInfo.setUpdateBy(ShiroUtils.getLoginName());
        roleInfo.setUpdateTime(DateUtils.getNowDate());
        roleInfoMapper.updateRoleInfo(roleInfo);
        ShiroUtils.clearCachedAuthorizationInfo();
        // 删除角色与菜单关联
        roleInfoMapper.deleteRoleMenuByRoleId(roleInfo.getRoleId());
        return insertRoleMenu(roleInfo);
    }

    /**
     * 删除角色信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRoleInfoByIds(String ids) {
        return roleInfoMapper.deleteRoleInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除角色信息信息
     *
     * @param roleId 角色信息ID
     * @return 结果
     */
    @Override
    public int deleteRoleInfoById(Long roleId) {
        return roleInfoMapper.deleteRoleInfoById(roleId);
    }

    @Override
    public void checkRoleAllowed(RoleInfo roleInfo) {
        if (roleInfo != null && roleInfo.isSuperAdmin()) {
            throw new GlobalException("不允许操作超级管理员角色");
        }
    }

    @Override
    public int changeStatus(RoleInfo roleInfo) {
        return roleInfoMapper.updateRoleInfo(roleInfo);
    }

    @Override
    @Transactional
    public int authDataScope(RoleInfo roleInfo) {
        roleInfo.setUpdateBy(ShiroUtils.getLoginName());
        roleInfo.setUpdateTime(DateUtils.getNowDate());
        // 修改角色信息
        roleInfoMapper.updateRoleInfo(roleInfo);
        // 删除角色与部门关联
        roleInfoMapper.deleteRoleDeptByRoleId(roleInfo.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(roleInfo);
    }

    @Override
    public int deleteAuthUser(UserRole userRole) {
        return userInfoMapper.deleteUserRoleInfo(userRole);
    }

    @Override
    public int insertAuthUserRoles(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<>();
        for (Long userId : users) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userInfoMapper.batchInsertUserRole(list);
    }

    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return userInfoMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 新增角色菜单信息
     *
     * @param roleInfo 角色对象
     */
    private int insertRoleMenu(RoleInfo roleInfo) {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<>();
        for (Long menuId : roleInfo.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(roleInfo.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleInfoMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param roleInfo 角色对象
     */
    private int insertRoleDept(RoleInfo roleInfo) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<RoleDept> list = new ArrayList<>();
        for (Long deptId : roleInfo.getDeptIds()) {
            RoleDept rd = new RoleDept();
            rd.setRoleId(roleInfo.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleInfoMapper.batchRoleDept(list);
        }
        return rows;
    }

}
