-- 标签分类
CREATE TABLE `tag_category` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `pid` bigint(19) NOT NULL COMMENT '父级ID，0代表顶层',
  `level` smallint(5) NOT NULL DEFAULT '1' COMMENT '分组层数',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `describe` varchar(1000) DEFAULT NULL COMMENT '描述',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_user_id` varchar(100) DEFAULT '' COMMENT '创建人',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_user_id` varchar(100) CHARACTER SET tis620 DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签分类表';

-- 标签分类关系表
CREATE TABLE `tag_category_relation` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `tag_category_id` bigint(19) NOT NULL COMMENT '组ID',
  `tag_id` varchar(100) NOT NULL COMMENT '标签ID',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '关联时间',
  PRIMARY KEY (`id`),
  KEY `tag_category_id_idx` (`tag_category_id`) USING BTREE,
  KEY `tag_id_idx` (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签和标签组多对多关联表';


INSERT INTO tag_category(id, pid, `level`, `name`, `describe`, del_flag, create_time, create_by, create_user_id, update_time, update_by, update_user_id) values (1,0, 1, '默认标签分类', '默认标签分类', 0, now(), 'admin', '0', now(), 'admin', '0');


INSERT INTO tag_category_relation(id, tag_category_id, tag_id, del_flag, create_time) SELECT id, 1, id, 0, now() FROM tag WHERE del_flag=0;


-- 增加系统全称字段
alter table daas.theme_resource add column full_name varchar(100) default null comment '系统全称' after `name`;