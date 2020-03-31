package com.guigui.perona.controller.router;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.SiteConstants;
import com.guigui.perona.common.dto.ArchivesWithArticle;
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
    public String errorRet() {
        return "error/500";
    }

    private void init(Model model) {
        //封装最新的8条文章数据 TODO 最近条数可配置
        List<Article> articleList = articleService.selectRecentArticles(8);
        model.addAttribute(SiteConstants.RECENT_POSTS, articleList);

        //封装最新的8条评论数据 TODO 最近条数可配置
        List<Comment> commentList = commentService.selectRecentComments(CommonConstants.DEFAULT_RELEASE_STATUS, 8);
        model.addAttribute(SiteConstants.RECENT_COMMENTS, commentList);
    }

    @RequestMapping({"", "/", "/page/{p}"})
    public String index(@PathVariable(required = false) String p, Model model) {
        try {
            PageInfo<Article> list = articlesCommon(p, null);
            list.getList().forEach(a -> {
                String content = Jsoup.parse(a.getContent()).text();
                if (content.length() > 50) {
                    content = content.substring(0, 40) + "...";
                }
                a.setContent(content);
                a.setContentMd(null);

                // TODO 可优化
                if (StringUtils.isNotBlank(a.getCategory())) {
                    Category category = categoryService.selectCategoryById(Long.valueOf(a.getCategory()));
                    if (category != null) {
                        a.setCategoryName(category.getName());
                    }
                }
            });
            Map<String, Object> data = super.getData(list);
            data.put("current", list.getPageNum());
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
            PageInfo<Article> list = articlesCommon(p, c);
            Category category = categoryService.selectCategoryById(Long.valueOf(c));
            categoryName = category.getName();
            list.getList().forEach(a -> {
                String content = Jsoup.parse(a.getContent()).text();
                if (content.length() > 50) {
                    content = content.substring(0, 40) + "...";
                }
                a.setContent(content);
                a.setContentMd(null);
                a.setCategoryName(category.getName());
            });
            Map<String, Object> data = super.getData(list);
            data.put("current", list.getPageNum());
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
            //封装评论数据
            Map<String, Object> map = commentService.getCommentsBySort(Integer.parseInt(page), SiteConstants.COMMENT_SORT_ABOUT, null);
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
            List<FriendLink> list = friendLinkService.selectFriendLinkList(new FriendLink());
            model.addAttribute(SiteConstants.LINKS_MODEL, list);

            //封装评论数据
            Map<String, Object> map = commentService.getCommentsBySort(Integer.parseInt(page), SiteConstants.COMMENT_SORT_LINKS, null);
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
            return this.errorRet();
        }
        try {
            if (page == null || page.equals("")) {
                page = "1";
            }
            if (StringUtils.isNotBlank(id)) {
                Article article = articleService.selectArticleById(Long.valueOf(id));
                if (article == null || article.getState().equals(CommonConstants.DEFAULT_DRAFT_STATUS)) {
                    return "redirect:/error/500";
                }
                model.addAttribute(SiteConstants.ARTICLE_MODEL, article);

                //封装该文章的评论数据
                Map<String, Object> map = commentService.getCommentsBySort(Integer.parseInt(page), SiteConstants.COMMENT_SORT_ARTICLE, id);
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

    private PageInfo<Article> articlesCommon(String p, String c) {
        if (StringUtils.isBlank(p)) {
            p = "1";
        }
        Article article = new Article();
        article.setState(CommonConstants.DEFAULT_RELEASE_STATUS);
        if (StringUtils.isNotBlank(c)) {
            article.setCategory(c);
        }
        PageHelper.startPage(Integer.parseInt(p), SiteConstants.DEFAULT_PAGE_LIMIT, "id desc");
        List<Article> list = articleService.selectArticleList(article);
        return new PageInfo<>(list);
    }
}
