package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.OperateLog;
import com.guigui.perona.service.IOperateLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Slf4j
@RestController
@RequestMapping("/api/log")
@Api(value = "LogController", tags = {"日志管理接口"})
public class OperateLogController extends BaseController {
    @Autowired
    private IOperateLogService logService;

    @GetMapping("/list")
    public Return findByPage(OperateLog log, QueryPage queryPage) {
        return new Return<>(super.getData(logService.list(log, queryPage)));
    }

    @Log("删除系统日志")
    @DeleteMapping("/{id}")
    @RequiresPermissions("log:delete")
    public Return delete(@PathVariable Long id) {
        try {
            logService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除操作日志出现异常！logId: {}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
