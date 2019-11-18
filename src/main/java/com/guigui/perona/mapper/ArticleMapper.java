package com.guigui.perona.mapper;

import com.guigui.perona.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<String> findArchivesDates();

    List<Article> findArchivesByDate(String date);
}
