package com.guigui.perona.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guigui.perona.common.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.CategoryMapper;
import com.guigui.perona.entity.Category;
import com.guigui.perona.service.ICategoryService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 分类Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-25
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询分类
     * 
     * @param id 分类ID
     * @return 分类
     */
    @Override
    public Category selectCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询分类列表
     * 
     * @param category 分类
     * @return 分类
     */
    @Override
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增分类
     * 
     * @param category 分类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category) {
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改分类
     * 
     * @param category 分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    /**
     * 删除分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids) {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除分类信息
     * 
     * @param id 分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long id) {
        return categoryMapper.deleteCategoryById(id);
    }

    @Override
    public List<Category> selectCategoryByArticleId(Long articleId) {
        return categoryMapper.selectCategoryByArticleId(articleId);
    }

    @Override
    public Map<Long, List<Category>> selectCategoryByArticleIds(List<Long> articleIds) {
        List<Category> categoryList = categoryMapper.selectCategoryByArticleIds(articleIds);
        Map<Long, List<Category>> result = new HashMap<>(categoryList.size());
        categoryList.forEach(category -> {
            Long articleId = category.getArticleId();
            List<Category> group = result.computeIfAbsent(articleId, k -> new ArrayList<>());
            group.add(category);
        });
        return result;
    }

    @Override
    public String checkCategoryUnique(Category category) {
        long categoryId = category.getId() == null ? -1L : category.getId();
        Category info = categoryMapper.selectCategoryByName(category.getName());
        if (info != null && info.getId() != categoryId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
