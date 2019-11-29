package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.ICommentService;
import com.guigui.perona.service.ILoginLogService;
import com.guigui.perona.service.IUserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "UserInfoController", tags = {"用户管理接口"})
public class UserInfoController extends BaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/info")
    public Return getInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("articleCount", articleService.count());
        map.put("commentCount", commentService.count());
        map.put("todayIp", 12);
        List<LoginLog> log = loginLogService.list();
        if (log == null || log.size() == 0) {
            map.put("lastLoginTime", null);
        } else {
            map.put("lastLoginTime", log.get(log.size() - 1).getCreateTime());
        }
        map.put("token", this.getSession().getId());
        map.put("user", this.getCurrentUser());
        return new Return<>(map);
    }

    @GetMapping("/{id}")
    public Return findById(@PathVariable Long id) {
        return new Return<>(userInfoService.getById(id));
    }

    @PostMapping
    @Log("新增用户")
    public Return save(@RequestBody UserInfo userInfo) {
        try {
            userInfoService.add(userInfo);
            return new Return();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新用户")
    public Return update(@RequestBody UserInfo userInfo) {
        try {
            userInfo.setId(this.getCurrentUser().getId());
            userInfoService.update(userInfo);
            return new Return();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除用户")
    public Return delete(@PathVariable Long id) {
        try {
            userInfoService.delete(id);
            return new Return();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

}
