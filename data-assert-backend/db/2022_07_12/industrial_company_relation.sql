/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.23-daas
Source Server Version : 50734
Source Host           : 192.168.0.23:3306
Source Database       : daas

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-07-12 15:29:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for industrial_company_relation
-- ----------------------------
DROP TABLE IF EXISTS `industrial_company_relation`;
CREATE TABLE `industrial_company_relation` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `industrial_id` varchar(32) NOT NULL COMMENT '标签id',
  `company_info_id` varchar(32) NOT NULL COMMENT '企业id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(64) DEFAULT NULL COMMENT '创建人id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(64) DEFAULT NULL COMMENT '更新人id',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0-否，1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产业-企业表多对多关系表';

INSERT INTO industrial_company_relation (
    `id`,
    `industrial_id`,
    `company_info_id`,
    `create_time`,
    `create_user_id`,
    `create_by`,
    `update_time`,
    `update_user_id`,
    `update_by`,
    `del_flag`
) SELECT
      round(rand()*10000000000000000000,0),
      '0',
      `id`,
      `create_time`,
      `create_user_id`,
      `create_by`,
      `update_time`,
      `update_user_id`,
      `update_by`,
      `del_flag`
from company_info;
