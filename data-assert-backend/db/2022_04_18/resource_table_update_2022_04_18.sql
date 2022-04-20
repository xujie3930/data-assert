-- 192.168.211.2
use daas;
DELETE FROM resource_table WHERE del_flag  = 'Y';
ALTER TABLE resource_table ADD UNIQUE `uk_resource_table_serial_num` (serial_num);

ALTER TABLE resource_table ADD `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据源类型：1-mysql,2-oracle,3-postgre';