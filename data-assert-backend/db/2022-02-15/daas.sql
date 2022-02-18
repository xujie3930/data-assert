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

use daas;
ALTER TABLE `daas`.`resource_table` ADD datasource_id varchar(32) COMMENT '数据源id';
ALTER TABLE `daas`.`table_setting` ADD desensitize_fields varchar(500) COMMENT '脱敏字段';
ALTER TABLE `daas`.`table_setting` ADD resp_info varchar(500) COMMENT '返回字段信息';

