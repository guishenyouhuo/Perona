package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.ArtTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface IArtTagService extends IService<ArtTag> {
    /**
     * 根据文章ID查询其关联的标签数据
     *
     * @param id
     * @return
     */
    List<ArtTag> findByArticleId(Long id);

    /**
     * 查询所有，为博客前台服务，查询并封装每个标签的文章数量
     *
     * @return
     */
    List<ArtTag> findAll();

    /**
     * 分页查询
     *
     * @param tag 查询条件
     * @return
     */
    IPage<ArtTag> list(ArtTag tag, QueryPage queryPage);

    /**
     * 新增
     *
     * @param tag
     */
    void add(ArtTag tag);

    /**
     * 更新
     *
     * @param tag
     */
    void update(ArtTag tag);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

}
