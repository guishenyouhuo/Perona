package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 根据文章ID查询其关联的分类数据
     *
     * @param id
     * @return
     */
    List<Category> findByArticleId(Long id);

    IPage<Category> list(Category sysCategory, QueryPage queryPage);

    void add(Category sysCategory);

    void update(Category sysCategory);

    void delete(Long id);

    Category findByName(String name);
}
