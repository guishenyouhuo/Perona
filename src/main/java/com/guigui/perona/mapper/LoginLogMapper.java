package com.guigui.perona.mapper;

import com.guigui.perona.entity.LoginLog;

import java.util.List;

/**
 * 登陆日志Mapper接口
 *
 * @author guigui
 * @date 2020-03-31
 */
public interface LoginLogMapper {
    /**
     * 根据主键查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    LoginLog selectLoginLogById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param loginLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 新增【请填写功能名称】
     *
     * @param loginLog 【请填写功能名称】
     * @return 结果
     */
    int insertLoginLog(LoginLog loginLog);

    /**
     * 修改【请填写功能名称】
     *
     * @param loginLog 【请填写功能名称】
     * @return 结果
     */
    int updateLoginLog(LoginLog loginLog);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    int deleteLoginLogById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLoginLogByIds(String[] ids);
}
