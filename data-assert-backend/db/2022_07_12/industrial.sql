/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.23-daas
Source Server Version : 50734
Source Host           : 192.168.0.23:3306
Source Database       : daas

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-07-12 15:27:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for industrial
-- ----------------------------
DROP TABLE IF EXISTS `industrial`;
CREATE TABLE `industrial` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(255) DEFAULT NULL COMMENT '产业库名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级目录id',
  `desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(64) DEFAULT NULL COMMENT '创建人id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(64) DEFAULT NULL COMMENT '更新人id',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除：0-否，1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产业库';

-- ----------------------------
-- Records of industrial
-- ----------------------------
INSERT INTO `industrial` VALUES ('0', '默认企业库', null, '默认产业库', '2022-07-07 19:22:50', '910626036754939904', 'admin', '2022-07-08 11:25:02', '910626036754939904', 'admin', '0');