package com.guigui.perona.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.ArticleCategoryMapper;
import com.guigui.perona.entity.ArticleCategory;
import com.guigui.perona.service.IArticleCategoryService;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 文章&&分类关联Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-26
 */
@Service
public class ArticleCategoryServiceImpl implements IArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 查询文章&&分类关联
     * 
     * @param id 文章&&分类关联ID
     * @return 文章&&分类关联
     */
    @Override
    public ArticleCategory selectArticleCategoryById(Long id) {
        return articleCategoryMapper.selectArticleCategoryById(id);
    }

    /**
     * 查询文章&&分类关联列表
     * 
     * @param articleCategory 文章&&分类关联
     * @return 文章&&分类关联
     */
    @Override
    public List<ArticleCategory> selectArticleCategoryList(ArticleCategory articleCategory) {
        return articleCategoryMapper.selectArticleCategoryList(articleCategory);
    }

    /**
     * 新增文章&&分类关联
     * 
     * @param articleCategory 文章&&分类关联
     * @return 结果
     */
    @Override
    public int insertArticleCategory(ArticleCategory articleCategory) {
        if (exists(articleCategory)) {
            return 0;
        }
        return articleCategoryMapper.insertArticleCategory(articleCategory);
    }

    /**
     * 修改文章&&分类关联
     * 
     * @param articleCategory 文章&&分类关联
     * @return 结果
     */
    @Override
    public int updateArticleCategory(ArticleCategory articleCategory) {
        return articleCategoryMapper.updateArticleCategory(articleCategory);
    }

    /**
     * 删除文章&&分类关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArticleCategoryByIds(String ids) {
        return articleCategoryMapper.deleteArticleCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文章&&分类关联信息
     * 
     * @param id 文章&&分类关联ID
     * @return 结果
     */
    @Override
    public int deleteArticleCategoryById(Long id) {
        return articleCategoryMapper.deleteArticleCategoryById(id);
    }

    @Override
    public void deleteByArticleId(Long articleId) {
        articleCategoryMapper.deleteByArticleId(articleId);
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        articleCategoryMapper.deleteByCategoryId(categoryId);
    }

    private boolean exists(ArticleCategory articleCategory) {
        List<ArticleCategory> list = articleCategoryMapper.selectArticleCategoryList(articleCategory);
        return !CollectionUtils.isEmpty(list);
    }

}
