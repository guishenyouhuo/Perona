package com.guigui.perona.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.common.utils.StringUtils;
import com.guigui.perona.manage.web.domain.Ztree;
import com.guigui.perona.mapper.DictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.DictTypeMapper;
import com.guigui.perona.entity.DictType;
import com.guigui.perona.service.IDictTypeService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 字典类型Service业务层处理
 *
 * @author guigui
 * @date 2020-03-13
 */
@Service
public class DictTypeServiceImpl implements IDictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 查询字典类型
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 查询字典类型列表
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 新增字典类型
     *
     * @param dictType 字典类型
     * @return 结果
     */
    @Override
    public int insertDictType(DictType dictType) {
        dictType.setCreateTime(DateUtils.getNowDate());
        dictType.setCreateBy(ShiroUtils.getLoginName());
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改字典类型
     *
     * @param dictType 字典类型
     * @return 结果
     */
    @Override
    public int updateDictType(DictType dictType) {
        DictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataByType(oldDict.getDictType(), dictType.getDictType());
        dictType.setUpdateTime(DateUtils.getNowDate());
        dictType.setUpdateBy(ShiroUtils.getLoginName());
        return dictTypeMapper.updateDictType(dictType);
    }

    /**
     * 删除字典类型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids) {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            DictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new GlobalException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }
        return dictTypeMapper.deleteDictTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除字典类型信息
     *
     * @param dictId 字典类型ID
     * @return 结果
     */
    @Override
    public int deleteDictTypeById(Long dictId) {
        return dictTypeMapper.deleteDictTypeById(dictId);
    }

    @Override
    public String checkDictTypeUnique(DictType dictType) {
        long dictId = dictType.getDictId() == null ? -1L : dictType.getDictId();
        DictType selectDictType = dictTypeMapper.checkDictTypeUnique(dictType.getDictType());
        if (selectDictType != null && selectDictType.getDictId() != dictId) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }

    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    @Override
    public DictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    @Override
    public List<Ztree> selectDictTree(DictType dictType) {
        List<Ztree> zTrees = new ArrayList<>();
        List<DictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
        for (DictType dict : dictList) {
            if (UserConstants.DICT_NORMAL.equals(dict.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                zTrees.add(ztree);
            }
        }
        return zTrees;
    }

    private String transDictName(DictType dictType) {
        return "(" + dictType.getDictName() + ")" +
                "&nbsp;&nbsp;&nbsp;" + dictType.getDictType();
    }

}
