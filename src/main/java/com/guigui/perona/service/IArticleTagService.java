package com.guigui.perona.service;

import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.entity.Article;
import com.guigui.perona.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章&&标签关联表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 根据文章ID查询文章+标签数据
     *
     * @param articleId
     * @return
     */
    List<ArtTag> findByArticleId(Long articleId);

    /**
     * 新增关联关系
     *
     * @param articleTag
     */
    void add(ArticleTag articleTag);

    /**
     * 根据标签ID查询文章+标签数据
     *
     * @param tagId
     * @return
     */
    List<ArticleTag> findByTagId(Long tagId);

    /**
     * 根据文章ID删除
     *
     * @param id
     */
    void deleteByArticleId(Long id);

    /**
     * 根据标签ID删除
     *
     * @param id
     */
    void deleteByTagsId(Long id);

    /**
     * 根据标签名称查询关联的文章
     *
     * @param tag
     * @return
     */
    List<Article> findByTagName(String tag);

}
