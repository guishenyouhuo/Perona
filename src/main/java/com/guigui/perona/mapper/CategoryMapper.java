package com.guigui.perona.mapper;

import com.guigui.perona.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper接口
 *
 * @author guigui
 * @date 2020-03-25
 */
public interface CategoryMapper {
    /**
     * 根据主键查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    Category selectCategoryById(Long id);

    /**
     * 根据名称查询分类
     *
     * @param name 分类名称
     * @return 分类
     */
    Category selectCategoryByName(String name);

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    List<Category> selectCategoryList(Category category);

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    int insertCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    int updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 结果
     */
    int deleteCategoryById(Long id);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCategoryByIds(String[] ids);

    /**
     * 根据文章Id查询分类
     *
     * @param articleId 文章id
     * @return 结果
     */
    List<Category> selectCategoryByArticleId(Long articleId);

    /**
     * 根据文章Id列表批量查询分类
     *
     * @param articleIds 文章id列表
     * @return 结果
     */
    List<Category> selectCategoryByArticleIds(@Param("articleIds") List<Long> articleIds);
}
