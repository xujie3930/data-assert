use daas;
CREATE TABLE `resource_pic` (
                                `id` varchar(32) NOT NULL COMMENT '主键id',
                                `pic_path` varchar(64) NOT NULL COMMENT '资源图标名称',
                                `pic_url` varchar(255) NOT NULL COMMENT '资源图标路径，minio地址',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源分类图标表';