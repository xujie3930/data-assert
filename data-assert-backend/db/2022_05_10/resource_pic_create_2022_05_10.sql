use daas;
CREATE TABLE `resource_pic` (
                                `id` varchar(32) NOT NULL COMMENT '主键id',
                                `pic_path` varchar(64) NOT NULL COMMENT '资源图标名称',
                                `pic_url` varchar(255) NOT NULL COMMENT '资源图标路径，minio地址',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源分类图标表';

use daas;
alter table `resource_table` add column `table_update_time` datetime DEFAULT NULL COMMENT '表更新时间';