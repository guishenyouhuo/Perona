package com.guigui.perona.controller;


import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.ICommentService;
import com.guigui.perona.service.ILoginLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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

}
