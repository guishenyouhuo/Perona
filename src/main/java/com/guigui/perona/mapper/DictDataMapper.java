package com.guigui.perona.mapper;

import com.guigui.perona.entity.DictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据Mapper接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface DictDataMapper {
    /**
     * 根据主键查询字典数据
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
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    DictData selectDictByTypeAndVal(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

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
     * 删除字典数据
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    int deleteDictDataById(Long dictCode);

    /**
     * 批量删除字典数据
     *
     * @param dictCodes 需要删除的数据ID
     * @return 结果
     */
    int deleteDictDataByIds(String[] dictCodes);

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    int countDictDataByType(String dictType);

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    int updateDictDataByType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
