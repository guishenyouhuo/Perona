package com.guigui.perona.mapper;

import com.guigui.perona.entity.ArticleTag;

import java.util.List;

/**
 * 文章&&标签关联Mapper接口
 *
 * @author guigui
 * @date 2020-03-26
 */
public interface ArticleTagMapper {
    /**
     * 根据主键查询文章&&标签关联
     *
     * @param id 文章&&标签关联ID
     * @return 文章&&标签关联
     */
    ArticleTag selectArticleTagById(Long id);

    /**
     * 查询文章&&标签关联列表
     *
     * @param articleTag 文章&&标签关联
     * @return 文章&&标签关联集合
     */
    List<ArticleTag> selectArticleTagList(ArticleTag articleTag);

    /**
     * 新增文章&&标签关联
     *
     * @param articleTag 文章&&标签关联
     * @return 结果
     */
    int insertArticleTag(ArticleTag articleTag);

    /**
     * 修改文章&&标签关联
     *
     * @param articleTag 文章&&标签关联
     * @return 结果
     */
    int updateArticleTag(ArticleTag articleTag);

    /**
     * 删除文章&&标签关联
     *
     * @param id 文章&&标签关联ID
     * @return 结果
     */
    int deleteArticleTagById(Long id);

    /**
     * 批量删除文章&&标签关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArticleTagByIds(String[] ids);

    /**
     * 根据文章id删除映射关系
     *
     * @param articleId 文章id
     */
    void deleteByArticleId(Long articleId);

    /**
     * 根据标签id删除映射关系
     *
     * @param tagId 标签id
     */
    void deleteByTagId(Long tagId);

    /**
     * 批量插入文章标签映射
     *
     * @param articleTagList 映射列表
     * @return 结果
     */
    int batchInsert(List<ArticleTag> articleTagList);
}
