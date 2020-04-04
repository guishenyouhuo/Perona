package com.guigui.perona.manage.controller;

import com.guigui.perona.common.BaseController;
import com.guigui.perona.common.properties.PeronaProperties;
import com.guigui.perona.common.utils.SpringContextUtils;
import com.guigui.perona.entity.MenuInfo;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author guigui
 */
@Controller
@RequestMapping("/manage")
public class IndexController extends BaseController {
    @Autowired
    private IMenuInfoService menuInfoService;

    private static PeronaProperties properties = SpringContextUtils.getBean(PeronaProperties.class);

    // 系统首页
    @GetMapping("")
    public String index(ModelMap mMap) {
        // 取身份信息
        UserInfo user = getCurrentUser();
        // 根据用户id取出菜单
        List<MenuInfo> menus = menuInfoService.selectMenuInfosByUser(user);
        mMap.put("menus", menus);
        mMap.put("user", user);
        mMap.put("copyrightYear", properties.getProject().getCopyrightYear());
        mMap.put("demoEnabled", properties.getProject().isDemoEnabled());
        return "manage/index" ;
    }

    // 切换主题
    @GetMapping("/switchSkin")
    public String switchSkin(ModelMap mMap) {
        return "manage/skin" ;
    }

    // 系统介绍
    @GetMapping("/main")
    public String main(ModelMap mMap) {
        mMap.put("version", properties.getProject().getVersion());
        return "manage/main" ;
    }
}
