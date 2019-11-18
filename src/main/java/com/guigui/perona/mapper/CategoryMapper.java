package com.guigui.perona.mapper;

import com.guigui.perona.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> findCategoryByArticleId(long id);
}
