/*
 Navicat Premium Data Transfer

 Source Server         : guigui(localhost)
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : perona

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 18/11/2019 16:44:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for art_tag
-- ----------------------------
DROP TABLE IF EXISTS `art_tag`;
CREATE TABLE `art_tag`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name` varchar(100) DEFAULT NULL COMMENT '标签名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='标签表';

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `title`        varchar(400) DEFAULT NULL COMMENT '标题',
    `cover`        varchar(400) DEFAULT NULL COMMENT '封面图片',
    `author`       varchar(100) NOT NULL COMMENT '作者',
    `content`      mediumtext COMMENT '内容',
    `content_md`   mediumtext COMMENT '内容-Markdown',
    `category`     varchar(20)  DEFAULT NULL COMMENT '分类',
    `origin`       varchar(100) DEFAULT NULL COMMENT '来源',
    `state`        varchar(100) NOT NULL COMMENT '状态',
    `publish_time` datetime     DEFAULT NULL COMMENT '发布时间',
    `edit_time`    datetime     NOT NULL COMMENT '上次修改时间',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `type`         int(11)      DEFAULT '0' COMMENT '类型， 0原创 1转载',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章表';

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `article_id`  bigint(20) NOT NULL COMMENT '文章ID',
    `category_id` bigint(20) NOT NULL COMMENT '分类ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章&&分类关联表';

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `article_id` bigint(20) NOT NULL COMMENT '文章ID',
    `tag_id`     bigint(20) NOT NULL COMMENT '标签ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章&&标签关联表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name` varchar(100) DEFAULT NULL COMMENT '分类名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='分类表';

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `p_id`          bigint(20)   DEFAULT '0' COMMENT '父级ID，给哪个留言进行回复',
    `c_id`          bigint(20)   DEFAULT '0' COMMENT '子级ID，给哪个留言下的回复进行评论',
    `article_title` varchar(200) DEFAULT NULL COMMENT '文章标题',
    `article_id`    bigint(20)   DEFAULT NULL COMMENT '文章ID',
    `name`          varchar(20)  DEFAULT NULL COMMENT '昵称',
    `c_name`        varchar(20)  DEFAULT NULL COMMENT '给谁留言',
    `time`          datetime   NOT NULL COMMENT '留言时间',
    `content`       text COMMENT '留言内容',
    `email`         varchar(100) DEFAULT NULL COMMENT '邮箱',
    `url`           varchar(200) DEFAULT NULL COMMENT '网址',
    `sort`          bigint(20)   DEFAULT '0' COMMENT '分类：0:默认，文章详情页，1:友链页，2:关于页',
    `ip`            varchar(20)  DEFAULT NULL COMMENT 'IP地址',
    `device`        varchar(100) DEFAULT NULL COMMENT '设备',
    `address`       varchar(100) DEFAULT NULL COMMENT '地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='评论表';

-- ----------------------------
-- Table structure for friend_link
-- ----------------------------
DROP TABLE IF EXISTS `friend_link`;
CREATE TABLE `friend_link`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name` varchar(100) DEFAULT NULL COMMENT '连接名称',
    `url`  varchar(200) DEFAULT NULL COMMENT '连接URL',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='友链表';

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username`    varchar(20)  DEFAULT NULL COMMENT '用户名',
    `ip`          varchar(20)  DEFAULT NULL COMMENT 'IP地址',
    `location`    varchar(255) DEFAULT NULL COMMENT '登录地点',
    `create_time` datetime     DEFAULT NULL COMMENT '登录时间',
    `device`      varchar(255) DEFAULT NULL COMMENT '登录设备',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE login_log
    ADD COLUMN `status` char(1) default '0' comment '登录状态（0成功 1失败）',
    ADD COLUMN `msg` varchar(256) default '' comment '提示消息';

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
create table `operate_log`
(
    id            bigint(20) not null auto_increment comment '编号',
    biz_name      varchar(32)   default '' comment '业务名称',
    biz_type      int(2)        default 0 comment '业务类型（0其它 1新增 2修改 3删除）',
    method        varchar(64)   default '' comment '方法名称',
    req_method    varchar(16)   default '' comment '请求方式',
    operate_type  int(1)        default 0 comment '操作类别（0其它 1后台用户 2手机端用户）',
    operator      varchar(32)   default '' comment '操作人员',
    dept_name     varchar(32)   default '' comment '部门名称',
    operate_url   varchar(128)  default '' comment '请求url',
    ip_addr       varchar(16)   default '' comment '主机地址',
    operate_loc   varchar(128)  default '' comment '操作地点',
    operate_param varchar(512)  default '' comment '请求参数',
    json_result   varchar(512)  default '' comment '返回参数',
    status        int(1)        default 0 comment '操作状态（0正常 1异常）',
    error_msg     varchar(512) default '' comment '错误消息',
    operate_time  datetime comment '操作时间',
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment = '操作日志记录';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`        bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username`  varchar(100) NOT NULL COMMENT '用户名',
    `password`  varchar(100) NOT NULL COMMENT '密码',
    `salt`      varchar(200) NOT NULL COMMENT '盐值',
    `avatar`    varchar(200) DEFAULT NULL COMMENT '头像',
    `introduce` varchar(100) DEFAULT NULL COMMENT '介绍',
    `remark`    varchar(100) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

SET FOREIGN_KEY_CHECKS = 1;


ALTER TABLE user_info
    ADD COLUMN `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID' AFTER `id`,
    ADD COLUMN `nickname` varchar(64) DEFAULT '' COMMENT '用户昵称' AFTER `username`,
    ADD COLUMN `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00普通用户 01管理员）' after `nickname`,
    ADD COLUMN `email` varchar(64) default '' comment '用户邮箱' after `user_type`,
    ADD COLUMN `phone_number` varchar(11) default '' comment '手机号码' after `email`,
    ADD COLUMN `sex` char(1) default '0' comment '用户性别（0男 1女 2未知）' after `phone_number`,
    ADD COLUMN `status` char(1) default '0' comment '帐号状态（0正常 1停用）' after `avatar`,
    ADD COLUMN `del_flag` char(1) default '0' comment '删除标志（0代表存在 2代表删除）' after `status`,
    ADD COLUMN `login_ip` varchar(32) default '' comment '最后登陆IP' after `del_flag`,
    ADD COLUMN `login_date` datetime comment '最后登陆时间' after `login_ip`,
    ADD COLUMN `create_by` varchar(64) default '' comment '创建者' after `login_date`,
    ADD COLUMN `create_time` datetime comment '创建时间' after `create_by`,
    ADD COLUMN `update_by` varchar(64) default '' comment '更新者' after `create_time`,
    ADD COLUMN `update_time` datetime comment '更新时间' after `update_by`;



-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists dept_info;
create table dept_info
(
    dept_id     bigint(20) not null auto_increment comment '部门编号',
    parent_id   bigint(20)  default 0 comment '父部门id',
    ancestors   varchar(64) default '' comment '上级列表',
    dept_name   varchar(32) default '' comment '部门名称',
    order_num   int         default 0 comment '显示顺序',
    leader      varchar(32) default null comment '负责人',
    phone       varchar(11) default null comment '联系电话',
    email       varchar(64) default null comment '邮箱',
    status      char(1)     default '0' comment '部门状态（0正常 1停用）',
    del_flag    char(1)     default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (dept_id)
) engine = innodb
  auto_increment = 1 comment = '部门表';

insert into dept_info
values (00000, 0000, '0000', '海贼世界', 0, '尾田荣一郎', '13599989988', 'wtryl@op.com', '0', '0', 'admin',
        '2020-03-01 12:55:00', 'admin',
        '2020-03-01 12:55:00');



-- ----------------------------
-- 2、角色信息表
-- ----------------------------
drop table if exists role_info;
create table role_info
(
    role_id     bigint(16)  not null auto_increment comment '角色编号',
    role_name   varchar(32) not null comment '角色名称',
    role_key    varchar(64) not null comment '角色key值',
    role_sort   int         not null comment '显示顺序',
    data_scope  char(1)      default '1' comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    status      char(1)     not null comment '角色状态（0正常 1停用）',
    del_flag    char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(512) default null comment '备注',
    primary key (role_id)
) engine = innodb
  auto_increment = 1 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into role_info
values ('1', '管理员', 'admin', 1, 1, '0', '0', 'admin', '2020-03-01 22:59:00', 'admin', '2020-03-01 22:59:00', '管理员角色');
insert into role_info
values ('2', '普通用户', 'users', 2, 2, '0', '0', 'admin', '2020-03-01 22:59:00', 'admin', '2020-03-01 22:59:00', '普通用户角色');



-- ----------------------------
-- 3、菜单权限表
-- ----------------------------
drop table if exists menu_info;
create table menu_info
(
    menu_id     bigint(20)  not null auto_increment comment '菜单编号',
    menu_name   varchar(64) not null comment '菜单名称',
    parent_id   bigint(20)   default 0 comment '父菜单ID',
    order_num   int          default 0 comment '显示顺序',
    url         varchar(64)  default '#' comment '请求地址',
    target      varchar(16)  default '' comment '打开方式（menuItem页签 menuBlank新窗口）',
    menu_type   char(1)      default '' comment '菜单类型（M目录 C菜单 F按钮）',
    visible     char(1)      default 0 comment '菜单状态（0显示 1隐藏）',
    perms       varchar(128) default null comment '权限标识',
    icon        varchar(128) default '#' comment '菜单图标',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(512) default '' comment '备注',
    primary key (menu_id)
) engine = innodb
  auto_increment = 1 comment = '菜单权限表';


-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into menu_info
values ('1', '系统管理', '0', '1', '#', '', 'M', '0', '', 'fa fa-gear', 'admin', '2020-03-01 22:59:00', 'admin',
        '2020-03-01 22:59:00', '系统管理目录');

-- 二级菜单
insert into menu_info
values ('100', '用户管理', '1', '1', '#', '', 'C', '0', 'system:user:view', '#', 'admin', '2020-03-01 22:59:00',
        'admin', '2020-03-01 22:59:00', '用户管理菜单');
insert into menu_info
values ('101', '角色管理', '1', '2', '#', '', 'C', '0', 'system:role:view', '#', 'admin', '2020-03-01 22:59:00',
        'admin', '2020-03-01 22:59:00', '角色管理菜单');

insert into menu_info
values ('102', '菜单管理', '1', '3', '#', '', 'C', '0', 'system:menu:view', '#', 'admin', '2020-03-01 22:59:00',
        'admin', '2020-03-01 22:59:00', '菜单管理菜单');
-- 菜单管理按钮
insert into menu_info
values ('1012', '菜单查询', '102', '1', '#', '', 'F', '0', 'manage:menu:list', '#', 'admin', '2020-03-01 22:59:00', 'admin',
        '2020-03-01 22:59:00', '');
insert into menu_info
values ('1013', '菜单新增', '102', '2', '#', '', 'F', '0', 'manage:menu:add', '#', 'admin', '2020-03-01 22:59:00', 'admin',
        '2020-03-01 22:59:00', '');
insert into menu_info
values ('1014', '菜单修改', '102', '3', '#', '', 'F', '0', 'manage:menu:edit', '#', 'admin', '2020-03-01 22:59:00', 'admin',
        '2020-03-01 22:59:00', '');
insert into menu_info
values ('1015', '菜单删除', '102', '4', '#', '', 'F', '0', 'manage:menu:remove', '#', 'admin', '2020-03-01 22:59:00', 'admin',
        '2020-03-01 22:59:00', '');


insert into menu_info
values ('103', '部门管理', '1', '4', '#', '', 'C', '0', 'system:dept:view', '#', 'admin', '2020-03-01 22:59:00',
        'admin', '2020-03-01 22:59:00', '部门管理菜单');



-- ----------------------------
-- 6、用户和角色关联表  用户N : 1角色
-- ----------------------------
drop table if exists user_role;
create table user_role
(
    user_id bigint(20) not null comment '用户ID',
    role_id bigint(20) not null comment '角色ID',
    primary key (user_id, role_id)
) engine = innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into user_role
values ('1', '1');
insert into user_role
values ('2', '2');



-- ----------------------------
-- 7、角色和菜单关联表  角色1 : N菜单
-- ----------------------------
drop table if exists role_menu;
create table role_menu
(
    role_id bigint(20) not null comment '角色ID',
    menu_id bigint(20) not null comment '菜单ID',
    primary key (role_id, menu_id)
) engine = innodb comment = '角色和菜单关联表';


-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into role_menu
values ('2', '1');
insert into role_menu
values ('2', '100');
insert into role_menu
values ('2', '101');
insert into role_menu
values ('2', '102');
insert into role_menu
values ('2', '103');

insert into role_menu values ('2', '1012');
insert into role_menu values ('2', '1013');
insert into role_menu values ('2', '1014');
insert into role_menu values ('2', '1015');


-- ----------------------------
-- 8、角色和部门关联表  角色1 : N部门
-- ----------------------------
drop table if exists role_dept;
create table role_dept
(
    role_id bigint(20) not null comment '角色ID',
    dept_id bigint(20) not null comment '部门ID',
    primary key (role_id, dept_id)
) engine = innodb comment = '角色和部门关联表';


-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
-- insert into role_dept values ('2', '100');
-- insert into role_dept values ('2', '101');
-- insert into role_dept values ('2', '105');


-- ----------------------------
-- 19、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table
(
    table_id        bigint not null auto_increment comment '编号',
    table_name      varchar(64)  default '' comment '表名称',
    table_comment   varchar(128) default '' comment '表描述',
    class_name      varchar(64)  default '' comment '实体类名称',
    tpl_category    varchar(64)  default 'crud' comment '使用的模板（crud单表操作 tree树表操作）',
    package_name    varchar(128) comment '生成包路径',
    module_name     varchar(32) comment '生成模块名',
    business_name   varchar(32) comment '生成业务名',
    function_name   varchar(64) comment '生成功能名',
    function_author varchar(64) comment '生成功能作者',
    options         varchar(1024) comment '其它生成选项',
    create_by       varchar(64)  default '' comment '创建者',
    create_time     datetime comment '创建时间',
    update_by       varchar(64)  default '' comment '更新者',
    update_time     datetime comment '更新时间',
    remark          varchar(512) default null comment '备注',
    primary key (table_id)
) engine = innodb
  auto_increment = 1 comment = '代码生成业务表';


-- ----------------------------
-- 20、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column
(
    column_id      bigint not null auto_increment comment '编号',
    table_id       varchar(64) comment '归属表编号',
    column_name    varchar(64) comment '列名称',
    column_comment varchar(256) comment '列描述',
    column_type    varchar(64) comment '列类型',
    java_type      varchar(64) comment 'JAVA类型',
    java_field     varchar(128) comment 'JAVA字段名',
    is_pk          char(1) comment '是否主键（1是）',
    is_increment   char(1) comment '是否自增（1是）',
    is_required    char(1) comment '是否必填（1是）',
    is_insert      char(1) comment '是否为插入字段（1是）',
    is_edit        char(1) comment '是否编辑字段（1是）',
    is_list        char(1) comment '是否列表字段（1是）',
    is_query       char(1) comment '是否查询字段（1是）',
    query_type     varchar(32) default 'EQ' comment '查询方式（等于、不等于、大于、小于、范围）',
    html_type      varchar(64) comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    dict_type      varchar(64) default '' comment '字典类型',
    sort           int comment '排序',
    create_by      varchar(64) default '' comment '创建者',
    create_time    datetime comment '创建时间',
    update_by      varchar(64) default '' comment '更新者',
    update_time    datetime comment '更新时间',
    primary key (column_id)
) engine = innodb
  auto_increment = 1 comment = '代码生成业务表字段';


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists param_config;
create table param_config
(
    config_id    int not null auto_increment comment '参数主键',
    config_name  varchar(128) default '' comment '参数名称',
    config_key   varchar(64)  default '' comment '参数键名',
    config_value varchar(128) default '' comment '参数键值',
    config_type  char(1)      default 'N' comment '系统内置（Y是 N否）',
    create_by    varchar(64)  default '' comment '创建者',
    create_time  datetime comment '创建时间',
    update_by    varchar(64)  default '' comment '更新者',
    update_time  datetime comment '更新时间',
    remark       varchar(512) default null comment '备注',
    primary key (config_id)
) engine = innodb
  auto_increment = 100 comment = '参数配置表';

insert into param_config
values (1, '主框架页-默认皮肤样式名称', 'index.skinName', 'skin-blue', 'Y', 'admin', '2020-03-13 11-50-00', 'admin',
        '2020-03-13 11-50-00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
insert into param_config
values (2, '用户管理-账号初始密码', 'user.initPassword', '123456', 'Y', 'admin', '2020-03-13 11-50-00', 'admin',
        '2020-03-13 11-50-00', '初始化密码 123456');
insert into param_config
values (3, '主框架页-侧边栏主题', 'index.sideTheme', 'theme-dark', 'Y', 'admin', '2020-03-13 11-50-00', 'admin',
        '2020-03-13 11-50-00', '深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue');



-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists dict_type;
create table dict_type
(
    dict_id     bigint not null auto_increment comment '字典主键',
    dict_name   varchar(128) default '' comment '字典名称',
    dict_type   varchar(64)  default '' comment '字典类型',
    status      char(1)      default '0' comment '状态（0正常 1停用）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(512) default null comment '备注',
    primary key (dict_id),
    unique (dict_type)
) engine = innodb
  auto_increment = 100 comment = '字典类型表';

insert into dict_type
values (1, '用户性别', 'user_sex', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '用户性别列表');
insert into dict_type
values (2, '菜单状态', 'menu_show_hide', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '菜单状态列表');
insert into dict_type
values (3, '系统开关', 'switch_normal_disable', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00',
        '系统开关列表');
insert into dict_type
values (4, '任务状态', 'job_status', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '任务状态列表');
insert into dict_type
values (5, '任务分组', 'job_group', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '任务分组列表');
insert into dict_type
values (6, '系统是否', 'sys_yes_no', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '系统是否列表');
insert into dict_type
values (7, '通知类型', 'notice_type', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '通知类型列表');
insert into dict_type
values (8, '通知状态', 'notice_status', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '通知状态列表');
insert into dict_type
values (9, '操作类型', 'operate_type', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '操作类型列表');
insert into dict_type
values (10, '系统状态', 'common_status', '0', 'admin', '2020-03-13 11-50-00', 'admin', '2020-03-13 11-50-00', '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists dict_data;
create table dict_data
(
    dict_code   bigint not null auto_increment comment '字典编码',
    dict_sort   int          default 0 comment '字典排序',
    dict_label  varchar(64)  default '' comment '字典标签',
    dict_value  varchar(64)  default '' comment '字典键值',
    dict_type   varchar(64)  default '' comment '字典类型',
    css_class   varchar(64)  default null comment '样式属性（其他样式扩展）',
    list_class  varchar(128) default null comment '表格回显样式',
    is_default  char(1)      default 'N' comment '是否默认（Y是 N否）',
    status      char(1)      default '0' comment '状态（0正常 1停用）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(512) default null comment '备注',
    primary key (dict_code)
) engine = innodb
  auto_increment = 100 comment = '字典数据表';

insert into dict_data
values (1, 1, '男', '0', 'user_sex', '', '', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin', '2020-03-13 16-03-00',
        '性别男');
insert into dict_data
values (2, 2, '女', '1', 'user_sex', '', '', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin', '2020-03-13 16-03-00',
        '性别女');
insert into dict_data
values (3, 3, '未知', '2', 'user_sex', '', '', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin', '2020-03-13 16-03-00',
        '性别未知');
insert into dict_data
values (4, 1, '显示', '0', 'menu_show_hide', '', 'primary', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '显示菜单');
insert into dict_data
values (5, 2, '隐藏', '1', 'menu_show_hide', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '隐藏菜单');
insert into dict_data
values (6, 1, '正常', '0', 'switch_normal_disable', '', 'primary', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '正常状态');
insert into dict_data
values (7, 2, '停用', '1', 'switch_normal_disable', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '停用状态');
insert into dict_data
values (8, 1, '正常', '0', 'job_status', '', 'primary', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '正常状态');
insert into dict_data
values (9, 2, '暂停', '1', 'job_status', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '停用状态');
insert into dict_data
values (10, 1, '默认', 'DEFAULT', 'job_group', '', '', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '默认分组');
insert into dict_data
values (11, 2, '系统', 'SYSTEM', 'job_group', '', '', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '系统分组');
insert into dict_data
values (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '系统默认是');
insert into dict_data
values (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '系统默认否');
insert into dict_data
values (14, 1, '通知', '1', 'notice_type', '', 'warning', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '通知');
insert into dict_data
values (15, 2, '公告', '2', 'notice_type', '', 'success', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '公告');
insert into dict_data
values (16, 1, '正常', '0', 'notice_status', '', 'primary', 'Y', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '正常状态');
insert into dict_data
values (17, 2, '关闭', '1', 'notice_status', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '关闭状态');
insert into dict_data
values (18, 1, '新增', '1', 'operate_type', '', 'info', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '新增操作');
insert into dict_data
values (19, 2, '修改', '2', 'operate_type', '', 'info', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '修改操作');
insert into dict_data
values (20, 3, '删除', '3', 'operate_type', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '删除操作');
insert into dict_data
values (21, 4, '授权', '4', 'operate_type', '', 'primary', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '授权操作');
insert into dict_data
values (22, 5, '导出', '5', 'operate_type', '', 'warning', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '导出操作');
insert into dict_data
values (23, 6, '导入', '6', 'operate_type', '', 'warning', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '导入操作');
insert into dict_data
values (24, 7, '强退', '7', 'operate_type', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '强退操作');
insert into dict_data
values (25, 8, '生成代码', '8', 'operate_type', '', 'warning', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '生成操作');
insert into dict_data
values (26, 9, '清空数据', '9', 'operate_type', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '清空操作');
insert into dict_data
values (27, 1, '成功', '0', 'common_status', '', 'primary', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '正常状态');
insert into dict_data
values (28, 2, '失败', '1', 'common_status', '', 'danger', 'N', '0', 'admin', '2020-03-13 16-03-00', 'admin',
        '2020-03-13 16-03-00', '停用状态');
