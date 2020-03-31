package com.guigui.perona.service;

import com.guigui.perona.entity.DictType;
import com.guigui.perona.manage.web.domain.Ztree;

import java.util.List;

/**
 * 字典类型Service接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface IDictTypeService {
    /**
     * 查询字典类型
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    DictType selectDictTypeById(Long dictId);

    /**
     * 查询字典类型列表
     *
     * @param dictType 字典类型
     * @return 字典类型集合
     */
    List<DictType> selectDictTypeList(DictType dictType);

    /**
     * 新增字典类型
     *
     * @param dictType 字典类型
     * @return 结果
     */
    int insertDictType(DictType dictType);

    /**
     * 修改字典类型
     *
     * @param dictType 字典类型
     * @return 结果
     */
    int updateDictType(DictType dictType);

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDictTypeByIds(String ids);

    /**
     * 删除字典类型信息
     *
     * @param dictId 字典类型ID
     * @return 结果
     */
    int deleteDictTypeById(Long dictId);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    String checkDictTypeUnique(DictType dictType);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<DictType> selectDictTypeAll();

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    DictType selectDictTypeByType(String dictType);

    /**
     * 查询字典类型树
     *
     * @param dictType 字典类型
     * @return 所有字典类型
     */
    List<Ztree> selectDictTree(DictType dictType);

}
