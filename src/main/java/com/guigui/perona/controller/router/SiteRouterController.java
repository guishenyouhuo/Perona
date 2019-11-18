package com.guigui.perona.controller.router;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.SiteConstants;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Article;
import com.guigui.perona.entity.Category;
import com.guigui.perona.entity.Comment;
import com.guigui.perona.entity.FriendLink;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.ICategoryService;
import com.guigui.perona.service.ICommentService;
import com.guigui.perona.service.IFriendLinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 站点路由控制器
 * @Author: guigui
 * @Date: 2019-10-23 21:14
 */
@Slf4j
@Controller
public class SiteRouterController extends BaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IFriendLinkService friendLinkService;

    @GetMapping("/error/500")
    public String error() {
        return "error/500";
    }

    private void init(Model model) {
        //封装最新的8条文章数据
        List<Article> articleList = articleService.findAll();
        articleList.forEach(a -> {
            a.setContent(null);
            a.setContentMd(null);
        });
        model.addAttribute(SiteConstants.RECENT_POSTS, articleList);

        //封装最新的8条评论数据
        List<Comment> commentList = commentService.findAll();
        model.addAttribute(SiteConstants.RECENT_COMMENTS, commentList);
    }

    @RequestMapping({"", "/", "/page/{p}"})
    public String index(@PathVariable(required = false) String p, Model model) {
        try {
            IPage<Article> list = articlesCommon(p, null);
            list.getRecords().forEach(a -> {
                String content = Jsoup.parse(a.getContent()).text();
                if (content.length() > 50) {
                    content = content.substring(0, 40) + "...";
                }
                a.setContent(content);
                a.setContentMd(null);

                if (StringUtils.isNotBlank(a.getCategory())) {
                    Category category = categoryService.getById(a.getCategory());
                    if (category != null) {
                        a.setCategoryName(category.getName());
                    }
                }
            });
            Map<String, Object> data = super.getData(list);
            data.put("current", list.getCurrent());
            data.put("pages", list.getPages());
            model.addAttribute(SiteConstants.INDEX_MODEL, data);

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("博客首页出现异常！", e);
            return "redirect:/error/500";
        }
        return "site/index";
    }

    @RequestMapping({"/category/{c}", "/category/{c}/page/{p}"})
    public String categoryArticles(@PathVariable String c, @PathVariable(required = false) String p, Model model) {
        String categoryName = "";
        try {
            IPage<Article> list = articlesCommon(p, c);
            Category category = categoryService.getById(c);
            categoryName = category.getName();
            list.getRecords().forEach(a -> {
                String content = Jsoup.parse(a.getContent()).text();
                if (content.length() > 50) {
                    content = content.substring(0, 40) + "...";
                }
                a.setContent(content);
                a.setContentMd(null);
                a.setCategoryName(category.getName());
            });
            Map<String, Object> data = super.getData(list);
            data.put("current", list.getCurrent());
            data.put("pages", list.getPages());
            data.put("category", categoryName);
            data.put("categoryId", c);
            model.addAttribute(SiteConstants.INDEX_MODEL, data);

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("【{}】类型博客列表打开出现异常！", categoryName, e);
            return "redirect:/error/500";
        }
        return "site/page/category_articles";
    }

    @RequestMapping("/about")
    public String about(@RequestParam(name = "page", required = false) String page, Model model) {
        try {
            if (page == null || page.equals("")) {
                page = "1";
            }
            QueryPage queryPage = new QueryPage(Integer.parseInt(page), SiteConstants.COMMENT_PAGE_LIMIT);
            //封装评论数据
            Map<String, Object> map = commentService.findCommentsList(queryPage, null, SiteConstants.COMMENT_SORT_ABOUT);
            model.addAttribute(SiteConstants.COMMENTS_MODEL, map);

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("about页面访问异常！", e);
            return "redirect:/error/500";
        }
        return "site/page/about";
    }

    /**
     * 友链页面
     *
     * @return
     */
    @RequestMapping("/links")
    public String links(@RequestParam(name = "page", required = false) String page, Model model) {
        try {
            if (page == null || page.equals("")) {
                page = "1";
            }
            QueryPage queryPage = new QueryPage(Integer.parseInt(page), SiteConstants.COMMENT_PAGE_LIMIT);
            List<FriendLink> list = friendLinkService.list();
            model.addAttribute(SiteConstants.LINKS_MODEL, list);

            //封装评论数据
            Map<String, Object> map = commentService.findCommentsList(queryPage, null, SiteConstants.COMMENT_SORT_LINKS);
            model.addAttribute(SiteConstants.COMMENTS_MODEL, map);

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("友情链接页面访问异常！", e);
            return "redirect:/error/500";
        }
        return "site/page/links";
    }

    /**
     * 归档页面
     *
     * @return
     */
    @RequestMapping("/archives")
    public String archives(Model model) {
        try {
            List<ArchivesWithArticle> list = articleService.findArchives();
            model.addAttribute(SiteConstants.ARCHIVES_MODEL, list);

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("归档页面访问异常！", e);
            return "redirect:/error/500";
        }
        return "site/page/archives";
    }

    /**
     * 文章详情页路由
     *
     * @param id 文章的Id
     * @return
     */
    @RequestMapping("/article/{id}")
    public String article(@PathVariable String id, @RequestParam(name = "page", required = false) String page, Model model) {
        if (id == null) {
            return this.error();
        }
        try {
            if (page == null || page.equals("")) {
                page = "1";
            }
            QueryPage queryPage = new QueryPage(Integer.parseInt(page), SiteConstants.COMMENT_PAGE_LIMIT);
            if (StringUtils.isNotBlank(id)) {
                Article article = articleService.findById(Long.valueOf(id));
                if (article == null || article.getState().equals(CommonConstants.DEFAULT_DRAFT_STATUS)) {
                    return "redirect:/error/500";
                }
                model.addAttribute(SiteConstants.ARTICLE_MODEL, article);

                //封装该文章的评论数据
                Map<String, Object> map = commentService.findCommentsList(queryPage, id, SiteConstants.COMMENT_SORT_ARTICLE);
                model.addAttribute(SiteConstants.COMMENTS_MODEL, map);
            }

            //初始化
            this.init(model);
        } catch (Exception e) {
            log.error("文章：{}页面访问异常！", id, e);
            return "redirect:/error/500";
        }
        return "site/page/article";
    }

    private IPage<Article> articlesCommon(String p, String c) {
        if (StringUtils.isBlank(p)) {
            p = "1";
        }
        IPage<Article> page = new Page<>(Integer.parseInt(p), SiteConstants.DEFAULT_PAGE_LIMIT);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        queryWrapper.eq(Article::getState, CommonConstants.DEFAULT_RELEASE_STATUS);
        if (StringUtils.isNotBlank(c)) {
            queryWrapper.eq(Article::getCategory, c);
        }
        return articleService.page(page, queryWrapper);
    }
}
