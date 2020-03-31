package com.guigui.perona.controller.admin;

import com.guigui.perona.entity.Article;
import com.guigui.perona.service.IArtTagService;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 后台管理Controller
 * @Author: guigui
 * @Date: 2019-11-01 15:28
 */
@Controller
public class AdminRouterController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArtTagService tagService;

    @GetMapping("/system")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String login() {
        return "manage/login";
    }

    @GetMapping("/admin/page/layout")
    public String layout() {
        return "admin/page/layout";
    }

    @GetMapping("/admin/page/index")
    public String index() {
        return "admin/page/index";
    }

    @RequestMapping("/admin/page/article")
    public String article() {
        return "admin/page/article/index";
    }

    @RequestMapping("/admin/page/article/add")
    public String articleAdd() {
        return "admin/page/article/form";
    }

//    @RequestMapping("/admin/page/article/edit/{id}")
//    public String articleEdit(Model model, @PathVariable String id) {
//        Article article = articleService.getById(id);
//        article.setArtTags(tagService.findByArticleId(article.getId()));
//        model.addAttribute("p", article);
//        return "admin/page/article/edit";
//    }

    @RequestMapping("/admin/page/tag")
    public String tag() {
        return "admin/page/tag/index";
    }

    @RequestMapping("/admin/page/tag/edit")
    public String tagEdit() {
        return "admin/page/tag/form";
    }

    @RequestMapping("/admin/page/category")
    public String category() {
        return "admin/page/category/index";
    }

    @RequestMapping("/admin/page/category/edit")
    public String categoryEdit() {
        return "admin/page/category/form";
    }

    @RequestMapping("/admin/page/link")
    public String link() {
        return "admin/page/link/index";
    }

    @RequestMapping("/admin/page/link/edit")
    public String linkEdit() {
        return "admin/page/link/form";
    }

    @RequestMapping("/admin/page/comment")
    public String comment() {
        return "admin/page/comment/index";
    }

    @RequestMapping("/admin/page/log")
    public String log() {
        return "admin/page/log/index";
    }

    @RequestMapping("/admin/page/loginlog")
    public String loginlog() {
        return "admin/page/loginlog/index";
    }

    @RequestMapping("/admin/page/user/profile")
    public String profile() {
        return "admin/page/user/profile";
    }

    @RequestMapping("/admin/page/user/avatar")
    public String avatar() {
        return "admin/page/user/avatar";
    }

    @RequestMapping("/admin/page/user/edit")
    public String userEdit() {
        return "admin/page/user/edit";
    }

    @RequestMapping("/admin/page/cloud")
    public String qiniu() {
        return "admin/page/cloud/index";
    }

    @RequestMapping("/admin/page/cloud/add")
    public String qiniuAdd() {
        return "admin/page/cloud/add";
    }

}
