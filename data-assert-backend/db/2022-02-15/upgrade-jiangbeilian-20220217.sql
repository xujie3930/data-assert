-- (1)添加字段
ALTER TABLE `daas`.`resource_table` ADD datasource_id varchar(32) COMMENT '数据源id';
ALTER TABLE `daas`.`table_setting` ADD desensitize_fields varchar(500) COMMENT '脱敏字段';
ALTER TABLE `daas`.`table_setting` ADD resp_info varchar(500) COMMENT '返回字段信息';

-- (2)初始化数据源库和表
SET FOREIGN_KEY_CHECKS=0;
CREATE database IF NOT EXISTS datasource;
-- ----------------------------
-- Table structure for bdl_sc_scheduling_process_fact_202111161157
-- ----------------------------

-- ----------------------------
-- Table structure for icredit_datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`.`icredit_datasource`;
CREATE TABLE `datasource`.`icredit_datasource` (
                                                   `id` varchar(32) NOT NULL COMMENT '主键id',
                                                   `space_id` varchar(32) DEFAULT NULL COMMENT '工作空间id',
                                                   `category` tinyint(4) NOT NULL DEFAULT '1',
                                                   `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据源类型',
                                                   `name` varchar(15) DEFAULT NULL COMMENT '数据源自定义名称',
                                                   `uri` varchar(255) DEFAULT NULL COMMENT '连接信息',
                                                   `status` tinyint(4) DEFAULT '0' COMMENT '是否启用：0-启用，1-非启用',
                                                   `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除',
                                                   `last_sync_time` datetime DEFAULT NULL COMMENT '最后一次同步时间',
                                                   `last_sync_status` tinyint(4) DEFAULT '2' COMMENT '最后一次同步状态：0-成功，1-失败,2未执行',
                                                   `descriptor` varchar(255) DEFAULT NULL COMMENT '描述信息',
                                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                   `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                   `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                                                   `show_password` tinyint(4) DEFAULT '0',
                                                   `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
                                                   `dialect` varchar(20) DEFAULT NULL,
                                                   `host` varchar(50) DEFAULT NULL,
                                                   `database_name` varchar(255) DEFAULT NULL,
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for icredit_ddl_sync
-- ----------------------------
DROP TABLE IF EXISTS `datasource`.`icredit_ddl_sync`;
CREATE TABLE `datasource`.`icredit_ddl_sync` (
                                                 `id` varchar(32) NOT NULL COMMENT '主键id',
                                                 `space_id` varchar(32) DEFAULT NULL COMMENT '工作空间id',
                                                 `datasource_id` varchar(32) DEFAULT NULL COMMENT 'datasource表的id',
                                                 `uri` varchar(255) DEFAULT NULL COMMENT '连接信息',
                                                 `columns_info` varchar(8000) DEFAULT NULL COMMENT '表所有信息',
                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                 `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                 `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                                                 `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
                                                 `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除',
                                                 `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
                                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- (3)添加初始化数据源
INSERT INTO `datasource`.`icredit_datasource` (`id`, `space_id`, `category`, `type`, `name`, `uri`, `status`, `del_flag`, `last_sync_time`, `last_sync_status`, `descriptor`, `create_time`, `create_by`, `update_time`, `update_by`, `show_password`, `remark`, `dialect`, `host`, `database_name`) VALUES ('943459149117698048', '', '1', '1', 'corporateData', 'jdbc:mysql://192.168.211.2:3306/corporate_data?username=corporate|password=ZcHqMIl0R4Rkair4G7eCkw==', '0', 'N', '2022-02-16 11:03:00', '0', '', '2022-02-16 10:49:50', '867486590626574336', '2022-02-16 10:49:50', '867486590626574336', '0', '', 'mysql', '192.168.0.17:3306', 'corporate_data');

-- (4)兼容旧数据，添加数据源ID
UPDATE `daas`.`resource_table` SET datasource_id=(SELECT id FROM `datasource`.`icredit_datasource` limit 1) where 1=1