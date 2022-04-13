/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : company_tag

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2022-04-12 18:55:48
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info`
(
    `id`                             varchar(32) NOT NULL COMMENT '主键id',
    `uscc`                           char(100)            DEFAULT NULL COMMENT '统一社会信用代码',
    `corp_nm`                        char(255)            DEFAULT NULL COMMENT '组织机构名称',
    `shrhlr_type`                    char(50)             DEFAULT NULL COMMENT '法人类型',
    `rgst_addr`                      char(255)            DEFAULT NULL COMMENT '注册地址',
    `rgst_addr_pstcd`                char(100)            DEFAULT NULL COMMENT '注册地址邮政编码',
    `rgst_addr_admn_did_nbr`         char(100)            DEFAULT NULL COMMENT '注册地行政区划代码',
    `operate_rng`                    longtext COMMENT '经营范围',
    `rgst_amo`                       varchar(100)         DEFAULT NULL COMMENT '注册资金',
    `shrhlr_nm`                      char(255)            DEFAULT NULL COMMENT '法定代表人姓名',
    `shrhlr_cer_type`                char(50)             DEFAULT NULL COMMENT '法定代表人身份证件类型',
    `shrhlr_cer_nbr`                 char(50)             DEFAULT NULL COMMENT '法定代表人身份证件号码',
    `shrhlr_ph`                      char(100)            DEFAULT NULL COMMENT '法人联系电话',
    `establish_dt`                   char(100)            DEFAULT NULL COMMENT '成立日期',
    `rgst_bsn_type`                  char(255)            DEFAULT NULL COMMENT '登记业务类型',
    `approval_dt`                    char(100)            DEFAULT NULL COMMENT '核准日期',
    `last_chg_dt`                    char(100)            DEFAULT NULL COMMENT '最后更新日期',
    `status`                         char(50)             DEFAULT NULL COMMENT '状态',
    `chrn_operate_rng`               char(255)            DEFAULT NULL COMMENT '生产经营地址',
    `chrn_operate_rng_admn_did`      char(100)            DEFAULT NULL COMMENT '生产经营地址行政区划',
    `fnd_mntr_type`                  char(50)             DEFAULT NULL COMMENT '货币种类',
    `rgst_nbr`                       char(255)            DEFAULT NULL COMMENT '注册号',
    `idy_type_code`                  char(100)            DEFAULT NULL COMMENT '行业分类代码',
    `ecn_type_code`                  char(100)            DEFAULT NULL COMMENT '经济类型代码',
    `enrl_admin_dep_nm`              char(255)            DEFAULT NULL COMMENT '登记管理部门名称',
    `enrl_admin_dep_nbr`             char(255)            DEFAULT NULL COMMENT '登记管理部门代码',
    `spvsr_unit`                     char(255)            DEFAULT NULL COMMENT '主管单位',
    `spvsr_unit_code`                char(100)            DEFAULT NULL COMMENT '主管单位代码',
    `business_circles_corp_tpye_nbr` char(100)            DEFAULT NULL COMMENT '工商企业类型代码',
    `strt_dt`                        char(100)            DEFAULT NULL COMMENT '有效期自',
    `end_dt`                         char(100)            DEFAULT NULL COMMENT '有效期至',
    `isno_open`                      char(30)             DEFAULT NULL COMMENT '是否公开',
    `describe`                       varchar(255)         DEFAULT NULL COMMENT '企业描述,200字内可以为空',
    `create_time`                    datetime             DEFAULT NULL COMMENT '创建时间',
    `create_user_id`                 varchar(64)          DEFAULT NULL COMMENT '创建人id',
    `create_by`                      varchar(64)          DEFAULT NULL COMMENT '创建人',
    `update_time`                    datetime             DEFAULT NULL COMMENT '更新时间',
    `update_user_id`                 varchar(64)          DEFAULT NULL COMMENT '更新人id',
    `update_by`                      varchar(64)          DEFAULT NULL COMMENT '更新人',
    `tag_num`                        int(11)     NOT NULL DEFAULT '0' COMMENT '该企业的标签数量',
    `del_flag`                       tinyint(1)  NOT NULL DEFAULT '0' COMMENT '是否删除:0-否，1-删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='企业信息表';

-- ----------------------------
-- Table structure for company_tag
-- ----------------------------
DROP TABLE IF EXISTS `company_tag`;
CREATE TABLE `company_tag`
(
    `id`              varchar(32) NOT NULL COMMENT '主键id',
    `tag_id`          varchar(32) NOT NULL COMMENT '标签id',
    `company_info_id` varchar(32) NOT NULL COMMENT '企业id',
    `create_time`     datetime             DEFAULT NULL COMMENT '创建时间',
    `create_user_id`  varchar(64)          DEFAULT NULL COMMENT '创建人id',
    `create_by`       varchar(64)          DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime             DEFAULT NULL COMMENT '更新时间',
    `update_user_id`  varchar(64)          DEFAULT NULL COMMENT '更新人id',
    `update_by`       varchar(64)          DEFAULT NULL COMMENT '更新人',
    `del_flag`        tinyint(1)  NOT NULL DEFAULT '0' COMMENT '是否删除:0-否，1-删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='企业标签关联表';

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `id`             varchar(32) NOT NULL COMMENT '主键id',
    `code`           varchar(32) NOT NULL COMMENT '标签代码:8位随机数，不为空且唯一',
    `name`           varchar(64) NOT NULL COMMENT '标签名称,50字内具有唯一性不为空',
    `describe`       varchar(255)         DEFAULT NULL COMMENT '标签描述,200字内可以为空',
    `state`          tinyint(4)  NOT NULL DEFAULT '1' COMMENT '是否启用：0-否，1-是',
    `used_time`      int(11)     NOT NULL DEFAULT '0' COMMENT '使用次数',
    `last_used_time` datetime             DEFAULT NULL COMMENT '最近使用时间',
    `create_time`    datetime    NOT NULL COMMENT '创建时间',
    `create_user_id` varchar(64) NOT NULL COMMENT '创建人id',
    `create_by`      varchar(64)          DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime    NOT NULL COMMENT '更新时间',
    `update_user_id` varchar(64) NOT NULL COMMENT '更新人id',
    `update_by`      varchar(64)          DEFAULT NULL COMMENT '更新人',
    `del_flag`       tinyint(1)  NOT NULL DEFAULT '0' COMMENT '是否删除:0-否，1-删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='标签表';
