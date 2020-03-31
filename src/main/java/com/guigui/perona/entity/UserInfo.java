package com.guigui.perona.entity;

import com.guigui.perona.common.constants.UserConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.guigui.perona.manage.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

/**
 * 用户对象 user_info
 *
 * @author guigui
 * @date 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 部门信息
     */
    private DeptInfo deptInfo;

    /**
     * 角色列表
     */
    private List<RoleInfo> roleList;

    /**
     * 选择角色ID
     */
    private Long selectRoleId;

    /**
     * 角色组
     */
    private Long[] roleIds;

    public UserInfo() {

    }

    public UserInfo(Long id) {
        this.id = id;
    }

    /**
     * 是否超级管理员
     *
     * @return
     */
    public boolean isSuperAdmin() {
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        }
        // 第一角色权限最大
        RoleInfo firstRole = roleList.get(0);
        return StringUtils.equals(firstRole.getRoleKey(), UserConstants.SUPER_ADMIN_KEY);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deptId", getDeptId())
                .append("username", getUsername())
                .append("nickname", getNickname())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phoneNumber", getPhoneNumber())
                .append("sex", getSex())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("avatar", getAvatar())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("introduce", getIntroduce())
                .append("remark", getRemark())
                .toString();
    }
}
