-- 开发环境：192.168.0.210:3306/iframe，username: root，password: H1lkkg!o(m<?
-- 测试环境：192.168.0.17:3306/jodpiframe，username: jodp，password: jodp@2018
-- 预发布环境：192.168.0.112:3306/jodpiframe

INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375669', '标签管理', 1, 'L-218', '925050527715164160', 1,
        '/**/dataresource/tag/**', 1, 3, NULL, NULL, '2022-02-25 12:43:14', NULL, NULL, NULL, NULL,
        '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);

INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375769', '企业管理', 1, 'L-219', '925050527715164160', 1,
        '/**/dataresource/company/**', 1, 3, NULL, NULL, '2022-02-25 12:43:14', NULL, NULL, NULL, NULL,
        '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);
