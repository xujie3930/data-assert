-- 开发环境：192.168.0.210:3306/iframe，username: root，password: H1lkkg!o(m<?
-- 测试环境：192.168.0.17:3306/jodpiframe，username: jodp，password: jodp@2018
-- 预发布环境：192.168.0.112:3306/jodpiframe

-- 初始化权限
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
    '925050527715164861',
    '企业标签管理',
    '0',
    'L-216',
    NULL,
    '1',
    '/companyTag/**',
    '1',
    '3',
    NULL,
    NULL,
    '2022-02-16 19:51:52',
    NULL,
    NULL,
    NULL,
    NULL,
    '2022-02-16 19:51:52',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);

