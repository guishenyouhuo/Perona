package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.entity.UserRole;
import com.guigui.perona.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.service.IRoleInfoService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 角色信息Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/role")
public class RoleInfoController extends BaseController {
    private String prefix = "manage/role";

    @Autowired
    private IRoleInfoService roleInfoService;

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    /**
     * 查询角色信息列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RoleInfo roleInfo) {
        startPage();
        List<RoleInfo> list = roleInfoService.selectRoleInfoList(roleInfo);
        return getDataTable(list);
    }

    /**
     * 新增角色信息
     */
    @GetMapping("/add")
    public String addview() {
        return prefix + "/add";
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(RoleInfo roleInfo) {
        return roleInfoService.checkRoleNameUnique(roleInfo);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(RoleInfo roleInfo) {
        return roleInfoService.checkRoleKeyUnique(roleInfo);
    }

    /**
     * 新增保存角色信息
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RoleInfo roleInfo) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleInfoService.checkRoleNameUnique(roleInfo))) {
            return error("新增角色'" + roleInfo.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleInfoService.checkRoleKeyUnique(roleInfo))) {
            return error("新增角色'" + roleInfo.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(roleInfoService.insertRoleInfo(roleInfo));
    }

    /**
     * 修改角色信息
     */
    @GetMapping("/edit/{roleId}")
    public String editView(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        RoleInfo roleInfo = roleInfoService.selectRoleInfoById(roleId);
        mmap.put("role", roleInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存角色信息
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RoleInfo roleInfo) {
        roleInfoService.checkRoleAllowed(roleInfo);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleInfoService.checkRoleNameUnique(roleInfo))) {
            return error("修改角色'" + roleInfo.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleInfoService.checkRoleKeyUnique(roleInfo))) {
            return error("修改角色'" + roleInfo.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(roleInfoService.updateRoleInfo(roleInfo));
    }

    /**
     * 删除角色信息
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(roleInfoService.deleteRoleInfoByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 启用/停用角色
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(RoleInfo roleInfo) {
        roleInfoService.checkRoleAllowed(roleInfo);
        return toAjax(roleInfoService.changeStatus(roleInfo));
    }

    /**
     * 角色分配数据权限
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleInfoService.selectRoleInfoById(roleId));
        return prefix + "/dataScope";
    }

    /**
     * 保存角色分配数据权限
     */
    @PostMapping("/authDataScope")
    @ResponseBody
    public AjaxResult authDataScopeSave(RoleInfo roleInfo) {
        roleInfoService.checkRoleAllowed(roleInfo);
        if (roleInfoService.authDataScope(roleInfo) > 0) {
            setUserInfo(userInfoService.selectUserInfoById(getCurrentUser().getId()));
            return success();
        }
        return error();
    }

    /**
     * 分配用户
     */
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleInfoService.selectRoleInfoById(roleId));
        return prefix + "/authUser";
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleInfoService.selectRoleInfoById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 查询已分配用户角色列表
     */
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(UserInfo userInfo) {
        startPage();
        List<UserInfo> list = userInfoService.selectAllocatedList(userInfo);
        return getDataTable(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(UserInfo userInfo) {
        startPage();
        List<UserInfo> list = userInfoService.selectUnAllocatedList(userInfo);
        return getDataTable(list);
    }

    /**
     * 取消授权
     */
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public AjaxResult cancelAuthUser(UserRole userRole) {
        return toAjax(roleInfoService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权
     */
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthUserAll(Long roleId, String userIds) {
        return toAjax(roleInfoService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @PostMapping("/authUser/authAllSelected")
    @ResponseBody
    public AjaxResult authAllSelect(Long roleId, String userIds) {
        return toAjax(roleInfoService.insertAuthUserRoles(roleId, userIds));
    }

}
