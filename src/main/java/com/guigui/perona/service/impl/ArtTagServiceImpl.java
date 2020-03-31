package com.guigui.perona.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guigui.perona.common.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.ArtTagMapper;
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.service.IArtTagService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 标签Service业务层处理
 *
 * @author guigui
 * @date 2020-03-25
 */
@Service
public class ArtTagServiceImpl implements IArtTagService {

    @Autowired
    private ArtTagMapper artTagMapper;

    /**
     * 查询标签
     *
     * @param id 标签ID
     * @return 标签
     */
    @Override
    public ArtTag selectArtTagById(Long id) {
        return artTagMapper.selectArtTagById(id);
    }

    /**
     * 查询标签列表
     *
     * @param artTag 标签
     * @return 标签
     */
    @Override
    public List<ArtTag> selectArtTagList(ArtTag artTag) {
        return artTagMapper.selectArtTagList(artTag);
    }

    /**
     * 新增标签
     *
     * @param artTag 标签
     * @return 结果
     */
    @Override
    public int insertArtTag(ArtTag artTag) {
        return artTagMapper.insertArtTag(artTag);
    }

    /**
     * 修改标签
     *
     * @param artTag 标签
     * @return 结果
     */
    @Override
    public int updateArtTag(ArtTag artTag) {
        return artTagMapper.updateArtTag(artTag);
    }

    /**
     * 删除标签对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArtTagByIds(String ids) {
        return artTagMapper.deleteArtTagByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除标签信息
     *
     * @param id 标签ID
     * @return 结果
     */
    @Override
    public int deleteArtTagById(Long id) {
        return artTagMapper.deleteArtTagById(id);
    }

    @Override
    public List<ArtTag> selectByArticleId(Long articleId) {
        return artTagMapper.selectByArticleId(articleId);
    }

    @Override
    public Map<Long, List<ArtTag>> selectByArticleIds(List<Long> articleIds) {
        List<ArtTag> artTagList = artTagMapper.selectByArticleIds(articleIds);
        Map<Long, List<ArtTag>> result = new HashMap<>(artTagList.size());
        artTagList.forEach(artTag -> {
            Long articleId = artTag.getArticleId();
            List<ArtTag> group = result.computeIfAbsent(articleId, k -> new ArrayList<>());
            group.add(artTag);
        });
        return result;
    }

    @Override
    public String checkArtTagUnique(ArtTag artTag) {
        long tagId = artTag.getId() == null ? -1L : artTag.getId();
        ArtTag info = artTagMapper.selectTagByName(artTag.getName());
        if (info != null && info.getId() != tagId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
