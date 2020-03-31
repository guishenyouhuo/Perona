package com.guigui.perona.mapper;

import com.guigui.perona.entity.Article;

import java.util.List;

/**
 * 文章Mapper接口
 *
 * @author guigui
 * @date 2020-03-25
 */
public interface ArticleMapper {
    /**
     * 根据主键查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article selectArticleById(Long id);

    /**
     * 根据标题查询文章(检查唯一性)
     *
     * @param title 文章标题
     * @return 文章
     */
    Article selectArticleTitle(String title);

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章集合
     */
    List<Article> selectArticleList(Article article);

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    int insertArticle(Article article);

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    int updateArticle(Article article);

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 结果
     */
    int deleteArticleById(Long id);

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArticleByIds(String[] ids);

    /**
     * 查询归档日期
     *
     * @return 结果
     */
    List<String> selectArchivesDates();

    /**
     * 按日期查找归档
     *
     * @param date 日期
     * @return 结果
     */
    List<Article> selectArchivesByDate(String date);

    /**
     * 查询最近文章数
     *
     * @param recentNum 最近数量
     * @return 文章列表
     */
    List<Article> selectRecent(int recentNum);
}
