package com.guigui.perona.mapper;

import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.entity.Article;
import com.guigui.perona.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文章&&标签关联表 Mapper 接口
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<ArtTag> findByArticleId(long articleId);

    List<Article> findByTagName(String name);
}
