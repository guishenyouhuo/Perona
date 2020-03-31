package com.guigui.perona.service;

import com.guigui.perona.entity.DictData;

import java.util.List;

/**
 * 字典数据Service接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface IDictDataService {
    /**
     * 查询字典数据
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    DictData selectDictDataById(Long dictCode);

    /**
     * 查询字典数据列表
     *
     * @param dictData 字典数据
     * @return 字典数据集合
     */
    List<DictData> selectDictDataList(DictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<DictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    DictData selectDictByTypeAndVal(String dictType, String dictValue);

    /**
     * 新增字典数据
     *
     * @param dictData 字典数据
     * @return 结果
     */
    int insertDictData(DictData dictData);

    /**
     * 修改字典数据
     *
     * @param dictData 字典数据
     * @return 结果
     */
    int updateDictData(DictData dictData);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDictDataByIds(String ids);

    /**
     * 删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    int deleteDictDataById(Long dictCode);

}
