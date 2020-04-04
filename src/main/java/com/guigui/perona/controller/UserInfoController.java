package com.guigui.perona.controller;

import java.util.List;
import java.util.Set;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.service.IRoleInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IUserInfoService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 用户Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/user")
public class UserInfoController extends BaseController {
    private String prefix = "manage/user";

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IRoleInfoService roleInfoService;

    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    /**
     * 查询用户列表
     */
    @RequiresPermissions("manage:user:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserInfo userInfo) {
        startPage();
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        return getDataTable(list);
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String addView(ModelMap mmap) {
        mmap.put("roles", roleInfoService.selectRoleInfoList(new RoleInfo()));
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("manage:user:add")
    @OperaLog(businessName = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserInfo userInfo) {
        if (UserConstants.USER_NOT_UNIQUE.equals(userInfoService.checkUserUnique(userInfo))) {
            return error("新增用户'" + userInfo.getUsername() + "'失败，该用户已存在!");
        }
        return toAjax(userInfoService.insertUserInfo(userInfo));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{id}")
    public String editView(@PathVariable("id") Long id, ModelMap mmap) {
        UserInfo userInfo = userInfoService.selectUserInfoById(id);
        List<RoleInfo> roleInfoList = roleInfoService.selectRoleInfoList(new RoleInfo());
        Set<String> roleSetByUser = roleInfoService.selectRoleKeysByUserId(id);
        for (RoleInfo roleInfo : roleInfoList) {
            if (roleSetByUser.contains(roleInfo.getRoleKey())) {
                roleInfo.setFlag(true);
            }
        }
        mmap.put("roles", roleInfoList);
        mmap.put("user", userInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("manage:user:edit")
    @OperaLog(businessName = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserInfo userInfo) {
        if (UserConstants.USER_NOT_UNIQUE.equals(userInfoService.checkUserUnique(userInfo))) {
            return error("修改用户信息'" + userInfo.getUsername() + "'失败，该用户已存在!");
        }
        return toAjax(userInfoService.updateUserInfo(userInfo));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("manage:user:remove")
    @OperaLog(businessName = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userInfoService.deleteUserInfoByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkUserUnique")
    @ResponseBody
    public String checkUserUnique(UserInfo userInfo) {
        return userInfoService.checkUserUnique(userInfo);
    }

    /**
     * 重置密码
     */
    @GetMapping("/resetPwd/{userId}")
    public String resetPwdView(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userInfoService.selectUserInfoById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("manage:user:reset")
    @OperaLog(businessName = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(UserInfo userInfo) {
        userInfoService.checkUserAllowed(userInfo);
        if (userInfoService.resetUserPwd(userInfo) > 0) {
            if (ShiroUtils.getUserId().equals(userInfo.getId())) {
                setUserInfo(userInfoService.selectUserInfoById(userInfo.getId()));
            }
            return success();
        }
        return error();
    }

    /**
     * 用户状态修改
     */
    @RequiresPermissions("manage:user:status")
    @OperaLog(businessName = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeUserStatus(UserInfo userInfo) {
        userInfoService.checkUserAllowed(userInfo);
        return toAjax(userInfoService.changeStatus(userInfo));
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(UserInfo userInfo) {
        return userInfoService.checkLoginNameUnique(userInfo);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(UserInfo userInfo) {
        return userInfoService.checkPhoneUnique(userInfo);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(UserInfo userInfo) {
        return userInfoService.checkEmailUnique(userInfo);
    }

}
