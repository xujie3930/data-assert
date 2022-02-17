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

use iframe;
INSERT INTO `iframe`.`sys_resource` (
    `sys_resource_id`,
    `name`,
    `is_leaf`,
    `code`,
    `parent_id`,
    `status`,
    `url`,
    `type`,
    `layout`,
    `order_by`,
    `remark`,
    `create_time`,
    `creator_id`,
    `creator_name`,
    `creator_depart_id`,
    `creator_depart_name`,
    `update_time`,
    `updater_id`,
    `updater_name`,
    `updater_depart_id`,
    `updater_depart_name`,
    `need_auth`
)
VALUES
(
    '925050527715164161',
    '数据源管理',
    '0',
    'L-187',
    NULL,
    '1',
    '/datasource/datasource/**',
    '1',
    '3',
    NULL,
    NULL,
    '2022-02-16 14:40:32',
    NULL,
    NULL,
    NULL,
    NULL,
    '2022-02-16 14:40:32',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);





