package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.entity.UserInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.Article;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 文章Controller
 *
 * @author guigui
 * @date 2020-03-25
 */
@Controller
@RequestMapping("/manage/article")
public class ArticleController extends BaseController {
    private String prefix = "manage/blog/article";

    @Autowired
    private IArticleService articleService;

    @GetMapping()
    public String article() {
        return prefix + "/article";
    }

    /**
     * 查询文章列表
     */
    @RequiresPermissions("manage:article:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Article article) {
        UserInfo currUser = ShiroUtils.getUserInfo();
        // 不是超级管理员只能看到自己写的文章
        if (!currUser.isSuperAdmin()) {
            article.setAuthor(currUser.getUsername());
        }
        startPage();
        List<Article> list = articleService.selectArticleList(article);
        return getDataTable(list);
    }

    /**
     * 新增文章
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存文章
     */
    @RequiresPermissions("manage:article:add")
    @OperaLog(businessName = "文章管理", businessType = BusinessType.INSERT, isSaveRequestData = false)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Article article) {
        if (UserConstants.NOT_UNIQUE.equals(articleService.checkArticleUnique(article))) {
            return error("新增文章'" + article.getTitle() + "'失败，文章标题已存在");
        }
        return toAjax(articleService.insertArticle(article));
    }

    /**
     * 修改文章
     */
    @GetMapping("/edit/{id}")
    public String editView(@PathVariable("id") Long id, ModelMap mmap) {
        Article article = articleService.selectArticleById(id);
        mmap.put("article", article);
        return prefix + "/edit";
    }

    /**
     * 修改保存文章
     */
    @RequiresPermissions("manage:article:edit")
    @OperaLog(businessName = "文章管理", businessType = BusinessType.UPDATE, isSaveRequestData = false)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Article article) {
        if (UserConstants.NOT_UNIQUE.equals(articleService.checkArticleUnique(article))) {
            return error("编辑文章'" + article.getTitle() + "'失败，文章标题已存在");
        }
        return toAjax(articleService.updateArticle(article));
    }

    /**
     * 删除文章
     */
    @RequiresPermissions("manage:article:remove")
    @OperaLog(businessName = "文章管理", businessType = BusinessType.DELETE, isSaveRequestData = false)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(articleService.deleteArticleByIds(ids));
    }

    /**
     * 查询所有的Archives
     */
    @GetMapping(value = "/archives")
    public List<ArchivesWithArticle> findArchives() {
        return articleService.findArchives();
    }

    /**
     * 校验文章标题
     */
    @PostMapping("/checkArticleUnique")
    @ResponseBody
    public String checkArticleUnique(Article article) {
        return articleService.checkArticleUnique(article);
    }

}
