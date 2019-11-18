package com.guigui.perona.service;

import com.guigui.perona.entity.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章&&分类关联表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface IArticleCategoryService extends IService<ArticleCategory> {
    /**
     * 新增
     *
     * @param articleCategory
     */
    void add(ArticleCategory articleCategory);

    /**
     * 根据文章ID删除
     *
     * @param id
     */
    void deleteByArticleId(Long id);

    /**
     * 根据分类ID删除
     *
     * @param id
     */
    void deleteByCategoryId(Long id);
}
