package com.guigui.perona.service;

import com.guigui.perona.entity.ArticleCategory;

import java.util.List;

/**
 * 文章&&分类关联Service接口
 *
 * @author guigui
 * @date 2020-03-26
 */
public interface IArticleCategoryService {
    /**
     * 查询文章&&分类关联
     *
     * @param id 文章&&分类关联ID
     * @return 文章&&分类关联
     */
    ArticleCategory selectArticleCategoryById(Long id);

    /**
     * 查询文章&&分类关联列表
     *
     * @param articleCategory 文章&&分类关联
     * @return 文章&&分类关联集合
     */
    List<ArticleCategory> selectArticleCategoryList(ArticleCategory articleCategory);

    /**
     * 新增文章&&分类关联
     *
     * @param articleCategory 文章&&分类关联
     * @return 结果
     */
    int insertArticleCategory(ArticleCategory articleCategory);

    /**
     * 修改文章&&分类关联
     *
     * @param articleCategory 文章&&分类关联
     * @return 结果
     */
    int updateArticleCategory(ArticleCategory articleCategory);

    /**
     * 批量删除文章&&分类关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArticleCategoryByIds(String ids);

    /**
     * 删除文章&&分类关联信息
     *
     * @param id 文章&&分类关联ID
     * @return 结果
     */
    int deleteArticleCategoryById(Long id);

    /**
     * 根据文章ID删除
     *
     * @param articleId 文章id
     */
    void deleteByArticleId(Long articleId);

    /**
     * 根据分类ID删除
     *
     * @param categoryId 分类id
     */
    void deleteByCategoryId(Long categoryId);

}
