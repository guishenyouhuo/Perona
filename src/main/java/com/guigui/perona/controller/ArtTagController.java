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
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.service.IArtTagService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 标签Controller
 *
 * @author guigui
 * @date 2020-03-25
 */
@Controller
@RequestMapping("/manage/tag")
public class ArtTagController extends BaseController {
    private String prefix = "manage/blog/tag";

    @Autowired
    private IArtTagService artTagService;

    @GetMapping()
    public String artTag() {
        return prefix + "/tag";
    }

    /**
     * 查询标签列表
     */
    @RequiresPermissions("manage:tag:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ArtTag artTag) {
        startPage();
        List<ArtTag> list = artTagService.selectArtTagList(artTag);
        return getDataTable(list);
    }

    /**
     * 新增标签
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存标签
     */
    @RequiresPermissions("manage:tag:add")
    @OperaLog(businessName = "文章标签", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ArtTag artTag) {
        if (UserConstants.NOT_UNIQUE.equals(artTagService.checkArtTagUnique(artTag))) {
            return error("新增标签'" + artTag.getName() + "'失败，标签名称已存在");
        }
        return toAjax(artTagService.insertArtTag(artTag));
    }

    /**
     * 修改标签
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ArtTag artTag = artTagService.selectArtTagById(id);
        mmap.put("artTag", artTag);
        return prefix + "/edit";
    }

    /**
     * 修改保存标签
     */
    @RequiresPermissions("manage:tag:edit")
    @OperaLog(businessName = "文章标签", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ArtTag artTag) {
        if (UserConstants.NOT_UNIQUE.equals(artTagService.checkArtTagUnique(artTag))) {
            return error("修改标签'" + artTag.getName() + "'失败，标签名称已存在");
        }
        return toAjax(artTagService.updateArtTag(artTag));
    }

    /**
     * 删除标签
     */
    @RequiresPermissions("manage:tag:remove")
    @OperaLog(businessName = "文章标签", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(artTagService.deleteArtTagByIds(ids));
    }

    /**
     * 校验标签名称
     */
    @PostMapping("/checkTagUnique")
    @ResponseBody
    public String checkTagUnique(ArtTag artTag) {
        return artTagService.checkArtTagUnique(artTag);
    }

}
