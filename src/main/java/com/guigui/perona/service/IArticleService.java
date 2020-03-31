package com.guigui.perona.service;

import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.entity.Article;

import java.util.List;

/**
 * 文章Service接口
 *
 * @author guigui
 * @date 2020-03-25
 */
public interface IArticleService {
    /**
     * 查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article selectArticleById(Long id);

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
     * 批量删除文章
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArticleByIds(String ids);

    /**
     * 删除文章信息
     *
     * @param id 文章ID
     * @return 结果
     */
    int deleteArticleById(Long id);

    /**
     * 按日期分类查询文章
     *
     * @return 结果
     */
    List<ArchivesWithArticle> findArchives();

    /**
     * 查询最近文章
     * @param recentNum 最近数量
     * @return 文章列表
     */
    List<Article> selectRecentArticles(int recentNum);

    /**
     * 校验文章是否唯一
     *
     * @param article 文章信息
     * @return 结果
     */
    String checkArticleUnique(Article article);

}
