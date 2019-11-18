package com.guigui.perona.common.constants;

/**
 * @Description: 站点常量集合
 * @Author: guigui
 * @Date: 2019-10-28 21:22
 */
public final class SiteConstants {

    /**
     * 博客前台默认每页显示多少条博文
     */
    public static final int DEFAULT_PAGE_LIMIT = 12;

    /**
     * Footer模块最新文章的Model对象Key值
     */
    public static final String RECENT_POSTS = "RecentPosts";

    /**
     * Footer模块最新评论的Model对象Key值
     */
    public static final String RECENT_COMMENTS = "RecentComments";

    /**
     * Index页面Model对象Key值
     */
    public static final String INDEX_MODEL = "list";

    /**
     * 文章详情页面Model对象Key值
     */
    public static final String ARTICLE_MODEL = "p";

    /**
     * 文章详情页评论数据Model对象Key值
     */
    public static final String COMMENTS_MODEL = "comments";

    /**
     * Archives页面Model对象Key值
     */
    public static final String ARCHIVES_MODEL = "list";

    /**
     * Links页面Model对象Key值
     */
    public static final String LINKS_MODEL = "list";

    /**
     * Articles页评论分类
     */
    public static final int COMMENT_SORT_ARTICLE = 0;

    /**
     * 默认每页显示多少条评论数据
     */
    public static final int COMMENT_PAGE_LIMIT = 8;

    /**
     * Links页评论分类
     */
    public static final int COMMENT_SORT_LINKS = 1;

    /**
     * About页评论分类
     */
    public static final int COMMENT_SORT_ABOUT = 2;
}
