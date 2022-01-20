/*micro-oauth2-api项目*/
/*插入数据权限*/
use jodpiframe;
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375396', '是否存在重复的表', 1, 'L-164', '925068022387834880', 1, '/*/resource/table/hasExist', 1, 3, NULL,
        NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375496', '是否存在开放状态的表', 1, 'L-165', '925068022387834880', 1,
        '/*/resource/table/hasExistOpenExternalState', 1, 3, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL,
        '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375596', '查看表基本信息', 1, 'L-166', '925068022387834880', 1, '/*/resource/table/info/baseInfo', 1, 3,
        NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375696', '查看表结构', 1, 'L-167', '925068022387834880', 1, '/*/resource/table/info/structureList', 1,
        3, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL,
        NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375796', '查看表采样数据', 1, 'L-168', '925068022387834880', 1, '/*/resource/table/info/sampleList', 1, 3,
        NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448385496', '是否存在开放状态的表', 1, 'L-180', '925068022387834880', 1,
        '/*/resource/table/hasExistOpenExternalState', 1, 3, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL,
        '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068423448375996', '查看资源分类下所有资源表', 1, 'L-210', '925068022387834880', 1, '/*/resource/table/List', 1, 3, NULL,
        NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623449376996', '编辑资源表状态', 1, 'L-211', '925068022387834880', 1, '/*/resource/table/update/state', 1, 3,
        NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);


/*新增字段*/
/*当前项目库*/
use daas;
alter table resource_table
    add column theme_id varchar(32) DEFAULT '' COMMENT '资源表所属主题id';
alter table resource_table
    add column `org_id` varchar(64) DEFAULT NULL COMMENT '部门id';
alter table resource_table
    add column `org_name` varchar(64) DEFAULT NULL COMMENT '部门名称';
alter table theme_resource
    add column create_user_id varchar(64) DEFAULT NULL COMMENT '创建者id';
alter table resource_table
    add column create_user_id varchar(64) DEFAULT NULL COMMENT '创建者id';
alter table theme_resource
    add column pic_path varchar(255) DEFAULT NULL COMMENT '分类图标名称';
alter table theme_resource
    add column pic_url varchar(255) DEFAULT NULL COMMENT '分类图标，存储图片的minio地址';
alter table table_setting
    add column `del_flag` char(1) DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除';
UPDATE table_setting
set del_flag = 'Y'
where resource_table_id in (select id from resource_table where del_flag = 'Y');