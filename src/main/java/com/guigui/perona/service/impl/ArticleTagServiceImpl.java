package com.guigui.perona.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.ArticleTagMapper;
import com.guigui.perona.entity.ArticleTag;
import com.guigui.perona.service.IArticleTagService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 文章&&标签关联Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-26
 */
@Service
public class ArticleTagServiceImpl implements IArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * 查询文章&&标签关联
     * 
     * @param id 文章&&标签关联ID
     * @return 文章&&标签关联
     */
    @Override
    public ArticleTag selectArticleTagById(Long id) {
        return articleTagMapper.selectArticleTagById(id);
    }

    /**
     * 查询文章&&标签关联列表
     * 
     * @param articleTag 文章&&标签关联
     * @return 文章&&标签关联
     */
    @Override
    public List<ArticleTag> selectArticleTagList(ArticleTag articleTag) {
        return articleTagMapper.selectArticleTagList(articleTag);
    }

    /**
     * 新增文章&&标签关联
     * 
     * @param articleTag 文章&&标签关联
     * @return 结果
     */
    @Override
    public int insertArticleTag(ArticleTag articleTag) {
        return articleTagMapper.insertArticleTag(articleTag);
    }

    /**
     * 修改文章&&标签关联
     * 
     * @param articleTag 文章&&标签关联
     * @return 结果
     */
    @Override
    public int updateArticleTag(ArticleTag articleTag) {
        return articleTagMapper.updateArticleTag(articleTag);
    }

    /**
     * 删除文章&&标签关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArticleTagByIds(String ids) {
        return articleTagMapper.deleteArticleTagByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文章&&标签关联信息
     * 
     * @param id 文章&&标签关联ID
     * @return 结果
     */
    @Override
    public int deleteArticleTagById(Long id) {
        return articleTagMapper.deleteArticleTagById(id);
    }

    @Override
    public void deleteByArticleId(Long articleId) {
        articleTagMapper.deleteByArticleId(articleId);
    }

    @Override
    public void deleteByTagId(Long tagId) {
        articleTagMapper.deleteByTagId(tagId);
    }

    @Override
    public int batchInsert(List<ArticleTag> articleTagList) {
        return articleTagMapper.batchInsert(articleTagList);
    }

}
