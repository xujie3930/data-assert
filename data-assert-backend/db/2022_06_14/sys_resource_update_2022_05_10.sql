-- 生产：192.168.211.2
-- 预发布：192.168.0.112
use jodpiframe;
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059302496927745', '查看图标列表', 1, 'L-224', '925052739900145664', 1, '/**/resource/pic/list', 1, 3, NULL,
        NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, NULL);

INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059302496927746', '上传分类图标', 1, 'L-225', '925052739900145664', 1, '/**/resource/pic/upload', 1, 3, NULL,
        NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, NULL);

INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059302496927747', '后端解析分类图标', 1, 'L-226', '925052739900145664', 1, '/**/resource/pic/iconDisplay', 1, 3, NULL,
        NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, NULL);

INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('932342837775695873', '查询应用列表', 1, 'L-227', '925068022387834880', 1, '/**/table/setting/app/groups', 1, 3, NULL,
NULL, now(), NULL, NULL, NULL, NULL, now(), NULL, NULL, NULL, NULL, NULL);
