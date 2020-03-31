package com.guigui.perona.mapper;

import com.guigui.perona.entity.ArtTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签Mapper接口
 *
 * @author guigui
 * @date 2020-03-25
 */
public interface ArtTagMapper {
    /**
     * 根据主键查询标签
     *
     * @param id 标签ID
     * @return 标签
     */
    ArtTag selectArtTagById(Long id);

    /**
     * 根据名称查询标签
     *
     * @param name 标签名称
     * @return 标签
     */
    ArtTag selectTagByName(String name);

    /**
     * 查询标签列表
     *
     * @param artTag 标签
     * @return 标签集合
     */
    List<ArtTag> selectArtTagList(ArtTag artTag);

    /**
     * 新增标签
     *
     * @param artTag 标签
     * @return 结果
     */
    int insertArtTag(ArtTag artTag);

    /**
     * 修改标签
     *
     * @param artTag 标签
     * @return 结果
     */
    int updateArtTag(ArtTag artTag);

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @return 结果
     */
    int deleteArtTagById(Long id);

    /**
     * 批量删除标签
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArtTagByIds(String[] ids);

    /**
     * 根据文章id查询标签列表
     *
     * @param articleId 文章编号
     * @return 结果
     */
    List<ArtTag> selectByArticleId(Long articleId);

    /**
     * 根据文章id批量列表查询标签列表
     *
     * @param articleIds 文章id列表
     * @return 结果
     */
    List<ArtTag> selectByArticleIds(@Param("articleIds")List<Long> articleIds);
}
