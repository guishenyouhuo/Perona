package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.FriendLink;
import com.guigui.perona.service.IFriendLinkService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 友链Controller
 * 
 * @author guigui
 * @date 2020-03-28
 */
@Controller
@RequestMapping("/manage/friend")
public class FriendLinkController extends BaseController {
    private String prefix = "manage/blog/friend";

    @Autowired
    private IFriendLinkService friendLinkService;

    @GetMapping()
    public String friend() {
        return prefix + "/friend";
    }

    /**
     * 查询友链列表
     */
    @RequiresPermissions("manage:friend:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FriendLink friendLink) {
        startPage();
        List<FriendLink> list = friendLinkService.selectFriendLinkList(friendLink);
        return getDataTable(list);
    }

    /**
     * 新增友链
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存友链
     */
    @RequiresPermissions("manage:friend:add")
    @OperaLog(businessName = "友链管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FriendLink friendLink) {
        if (UserConstants.NOT_UNIQUE.equals(friendLinkService.checkFriendLinkUnique(friendLink))) {
            return error("新增友链'" + friendLink.getName() + "'失败，友链名称已存在");
        }
        return toAjax(friendLinkService.insertFriendLink(friendLink));
    }

    /**
     * 修改友链
     */
    @GetMapping("/edit/{id}")
    public String editView(@PathVariable("id") Long id, ModelMap mmap) {
        FriendLink friendLink = friendLinkService.selectFriendLinkById(id);
        mmap.put("friendLink", friendLink);
        return prefix + "/edit";
    }

    /**
     * 修改保存友链
     */
    @RequiresPermissions("manage:friend:edit")
    @OperaLog(businessName = "友链管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FriendLink friendLink) {
        if (UserConstants.NOT_UNIQUE.equals(friendLinkService.checkFriendLinkUnique(friendLink))) {
            return error("修改友链'" + friendLink.getName() + "'失败，友链名称已存在");
        }
        return toAjax(friendLinkService.updateFriendLink(friendLink));
    }

    /**
     * 删除友链
     */
    @RequiresPermissions("manage:friend:remove")
    @OperaLog(businessName = "友链管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(friendLinkService.deleteFriendLinkByIds(ids));
    }

    /**
     * 校验友链名称
     */
    @PostMapping("/checkLinkUnique")
    @ResponseBody
    public String checkLinkUnique(FriendLink friendLink) {
        return friendLinkService.checkFriendLinkUnique(friendLink);
    }

}
