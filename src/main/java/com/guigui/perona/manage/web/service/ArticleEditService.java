package com.guigui.perona.manage.web.service;

import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.entity.Category;
import com.guigui.perona.service.IArtTagService;
import com.guigui.perona.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * thymeleaf特性 html直接调用 文章编辑相关服务
 *
 * @author guigui
 */
@Service("articleEdit")
public class ArticleEditService {

    @Autowired
    private IArtTagService artTagService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查找所有标签列表
     *
     * @return 标签列表
     */
    public List<ArtTag> getArtTags() {
        return artTagService.selectArtTagList(new ArtTag());
    }

    /**
     * 查找所有分类列表
     * @return 分类列表
     */
    public List<Category> getCategories() {
        return categoryService.selectCategoryList(new Category());
    }

}
