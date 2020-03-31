package com.guigui.perona.service;

import com.guigui.perona.entity.ArtTag;
import java.util.List;
import java.util.Map;

/**
 * 标签Service接口
 * 
 * @author guigui
 * @date 2020-03-25
 */
public interface IArtTagService {
    /**
     * 查询标签
     * 
     * @param id 标签ID
     * @return 标签
     */
    ArtTag selectArtTagById(Long id);

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
     * 批量删除标签
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteArtTagByIds(String ids);

    /**
     * 删除标签信息
     * 
     * @param id 标签ID
     * @return 结果
     */
    int deleteArtTagById(Long id);

    /**
     * 根据文章ID查询其关联的标签数据
     *
     * @param articleId 文章id
     * @return 结果
     */
    List<ArtTag> selectByArticleId(Long articleId);

    /**
     * 根据文章ID列表查询其关联的标签数据
     *
     * @param articleIds 文章id列表
     * @return 结果
     */
    Map<Long, List<ArtTag>> selectByArticleIds(List<Long> articleIds);

    /**
     * 校验标签名是否唯一
     *
     * @param artTag 标签信息
     * @return 结果
     */
    String checkArtTagUnique(ArtTag artTag);

}
