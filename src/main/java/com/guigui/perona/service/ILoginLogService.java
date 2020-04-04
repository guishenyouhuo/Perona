package com.guigui.perona.service;

import com.guigui.perona.entity.LoginLog;

import java.util.List;

/**
 * 登陆日志Service接口
 *
 * @author guigui
 * @date 2020-03-31
 */
public interface ILoginLogService {
    /**
     * 查询登陆日志
     *
     * @param id 登陆日志ID
     * @return 登陆日志
     */
    LoginLog selectLoginLogById(Long id);

    /**
     * 查询登陆日志列表
     *
     * @param loginLog 登陆日志
     * @return 登陆日志集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 新增登陆日志
     *
     * @param loginLog 登陆日志
     * @return 结果
     */
    int insertLoginLog(LoginLog loginLog);

    /**
     * 修改登陆日志
     *
     * @param loginLog 登陆日志
     * @return 结果
     */
    int updateLoginLog(LoginLog loginLog);

    /**
     * 批量删除登陆日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLoginLogByIds(String ids);

    /**
     * 删除登陆日志信息
     *
     * @param id 登陆日志ID
     * @return 结果
     */
    int deleteLoginLogById(Long id);

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();

}
