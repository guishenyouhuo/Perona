package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.entity.Article;
import com.guigui.perona.entity.ArticleTag;
import com.guigui.perona.mapper.ArticleTagMapper;
import com.guigui.perona.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 文章&&标签关联表 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArtTag> findByArticleId(Long articleId) {
        return articleTagMapper.findByArticleId(articleId);
    }

    @Override
    @Transactional
    public void add(ArticleTag articleTag) {
        if (!exists(articleTag)) {
            articleTagMapper.insert(articleTag);
        }
    }

    private boolean exists(ArticleTag articleTag) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleTag.getArticleId());
        queryWrapper.eq(ArticleTag::getTagId, articleTag.getTagId());
        return articleTagMapper.selectList(queryWrapper).size() > 0;
    }

    @Override
    public List<ArticleTag> findByTagId(Long tagId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId, tagId);
        return articleTagMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByTagsId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(queryWrapper);
    }

    @Override
    public List<Article> findByTagName(String tag) {
        return articleTagMapper.findByTagName(tag);
    }
}
