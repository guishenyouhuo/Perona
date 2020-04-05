package com.guigui.perona.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 操作日志记录对象 operate_log
 *
 * @author guigui
 * @date 2020-04-02
 */
@Data
@Accessors(chain = true)
public class OperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer bizType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operateType;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求url
     */
    private String operateUrl;

    /**
     * 主机地址
     */
    private String ipAddr;

    /**
     * 操作地点
     */
    private String operateLoc;

    /**
     * 请求参数
     */
    private String operateParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /**
     * 请求参数
     */
    private Map<String, Object> params = Maps.newHashMap();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("bizName", getBizName())
                .append("bizType", getBizType())
                .append("method", getMethod())
                .append("reqMethod", getReqMethod())
                .append("operateType", getOperateType())
                .append("operator", getOperator())
                .append("deptName", getDeptName())
                .append("operateUrl", getOperateUrl())
                .append("ipAddr", getIpAddr())
                .append("operateLoc", getOperateLoc())
                .append("operateParam", getOperateParam())
                .append("jsonResult", getJsonResult())
                .append("status", getStatus())
                .append("errorMsg", getErrorMsg())
                .append("operateTime", getOperateTime())
                .toString();
    }
}
