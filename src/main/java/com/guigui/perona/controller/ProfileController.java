package com.guigui.perona.controller;

import com.guigui.perona.common.BaseController;
import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.common.utils.PasswordHelper;
import com.guigui.perona.common.utils.StringUtils;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人信息 业务处理
 *
 * @author guigui
 */
@Controller
@RequestMapping("/manage/user/profile")
public class ProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "manage/user/profile";

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        UserInfo userInfo = getCurrentUser();
        mmap.put("user", userInfo);
        mmap.put("roleGroup", userInfoService.selectUserRoleGroup(userInfo.getId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        UserInfo userInfo = getCurrentUser();
        return passwordHelper.matchPwd(userInfo, password);
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap) {
        UserInfo userInfo = getCurrentUser();
        mmap.put("user", userInfoService.selectUserInfoById(userInfo.getId()));
        return prefix + "/resetPwd";
    }

    @OperaLog(businessName = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword) {
        UserInfo userInfo = getCurrentUser();
        if (StringUtils.isNotEmpty(newPassword) && passwordHelper.matchPwd(userInfo, oldPassword)) {
            userInfo.setPassword(newPassword);
            if (userInfoService.resetUserPwd(userInfo) > 0) {
                setUserInfo(userInfoService.selectUserInfoById(userInfo.getId()));
                return success();
            }
            return error();
        } else {
            return error("修改密码失败，旧密码错误");
        }

    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        UserInfo userInfo = getCurrentUser();
        mmap.put("user", userInfoService.selectUserInfoById(userInfo.getId()));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        UserInfo userInfo = getCurrentUser();
        mmap.put("user", userInfoService.selectUserInfoById(userInfo.getId()));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @OperaLog(businessName = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(UserInfo userInfo) {
        UserInfo currentUser = getCurrentUser();
        currentUser.setUsername(userInfo.getUsername());
        currentUser.setEmail(userInfo.getEmail());
        currentUser.setPhoneNumber(userInfo.getPhoneNumber());
        currentUser.setSex(userInfo.getSex());
        if (userInfoService.updateUserRecord(currentUser) > 0) {
            setUserInfo(userInfoService.selectUserInfoById(currentUser.getId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
//    @PostMapping("/updateAvatar")
//    @ResponseBody
//    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
//        UserInfo currentUser = getCurrentUser();
//        try {
//            if (!file.isEmpty()) {
//                String avatar = FileUploadUtils.upload(Config.getAvatarPath(), file);
//                currentUser.setAvatar(avatar);
//                if (userInfoService.updateUserRecord(currentUser) > 0) {
//                    setUserInfo(userInfoService.selectUserInfoById(currentUser.getId()));
//                    return success();
//                }
//            }
//            return error();
//        } catch (Exception e) {
//            log.error("修改头像失败！", e);
//            return error(e.getMessage());
//        }
//    }
}
