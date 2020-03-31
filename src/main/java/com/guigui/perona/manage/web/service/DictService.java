package com.guigui.perona.manage.web.service;

import com.guigui.perona.entity.DictData;
import com.guigui.perona.service.IDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * thymeleaf特性 html直接调用 实现字典读取
 *
 * @author guigui
 */
@Service("dict")
public class DictService {
    @Autowired
    private IDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<DictData> getType(String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue) {
        DictData dictData = dictDataService.selectDictByTypeAndVal(dictType, dictValue);
        return dictData != null ? dictData.getDictLabel() : "" ;
    }
}
