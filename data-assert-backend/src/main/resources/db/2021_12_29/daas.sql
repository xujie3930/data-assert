/*
Navicat MySQL Data Transfer

Source Server         : 0.23内网
Source Server Version : 50734
Source Host           : 192.168.0.23:3306
Source Database       : daas

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-12-17 10:18:39
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT COMMENT '数据来源id',
    `name` varchar(255) DEFAULT '' COMMENT '数据来源描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据来源表';

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source`
VALUES ('1', '企查查接口');
INSERT INTO `data_source`
VALUES ('2', '江北新区大数据管理中心数据');

-- ----------------------------
-- Table structure for resource_table
-- ----------------------------
DROP TABLE IF EXISTS `resource_table`;
CREATE TABLE `resource_table`
(
    `id`             varchar(32)  DEFAULT NULL COMMENT '主键id',
    `resource_id`    varchar(32)  DEFAULT NULL COMMENT '关联theme_resource表的id',
    `state`          tinyint(4)   DEFAULT NULL COMMENT '0-开放，1-不开放',
    `external_state` tinyint(4)   DEFAULT '1' COMMENT '是否在开放平台开放：0-开放，1-不开放',
    `name`           varchar(50)  DEFAULT NULL COMMENT '资源分类名称',
    `request_url`    varchar(500) DEFAULT NULL COMMENT '访问表的URL',
    `source`         varchar(500) DEFAULT NULL COMMENT '当前表数据来源',
    `columns_count`  int(11)      DEFAULT NULL COMMENT '当前表的字段数',
    `data_size`      bigint(20)   DEFAULT '0' COMMENT '数据量大小',
    `matters`        varchar(200) DEFAULT NULL COMMENT '注意事项',
    `descriptor`     varchar(200) DEFAULT NULL COMMENT '资源分类描述',
    `sort`           int(11)      DEFAULT NULL COMMENT '用于排序的字段',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`      varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by`      varchar(64)  DEFAULT NULL COMMENT '更新人',
    `del_flag`       char(1)      DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资源表';

-- ----------------------------
-- Table structure for table_setting
-- ----------------------------
DROP TABLE IF EXISTS `table_setting`;
CREATE TABLE `table_setting`
(
    `id`                varchar(32)  DEFAULT NULL COMMENT '主键id',
    `resource_table_id` varchar(32)  DEFAULT NULL COMMENT '关联resource_table表的id',
    `formats`           tinyint(4)   DEFAULT '0' COMMENT '支持格式：0-JSON',
    `request_way`       tinyint(4)   DEFAULT '0' COMMENT '请求方式：0-POST,1-GET',
    `explain_info`      varchar(500) DEFAULT NULL COMMENT '请求实例说明',
    `param_info`        varchar(500) DEFAULT NULL COMMENT '参数信息'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资源信息设置表';

-- ----------------------------
-- Table structure for theme_resource
-- ----------------------------
DROP TABLE IF EXISTS `theme_resource`;
CREATE TABLE `theme_resource`
(
    `id`          varchar(32)  DEFAULT NULL COMMENT '主键id',
    `parent_id`   varchar(32)  DEFAULT NULL COMMENT '上级目录id，没有则为0',
    `name`        varchar(50)  DEFAULT NULL COMMENT '主题或者资源分类名称',
    `descriptor`  varchar(200) DEFAULT NULL COMMENT '主题或者资源分类描述',
    `sort`        int(11)      DEFAULT NULL COMMENT '用于主题和资源分类排序',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    `del_flag`    char(1)      DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='主题资源表';

