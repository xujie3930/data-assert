-- 接口资源权限
INSERT INTO `jodpiframe`.`sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`,
                                         `type`, `layout`, `order_by`, `remark`, `create_time`, `creator_id`,
                                         `creator_name`, `creator_depart_id`, `creator_depart_name`, `update_time`,
                                         `updater_id`, `updater_name`, `updater_depart_id`, `updater_depart_name`,
                                         `need_auth`)
VALUES ('925061126918504458', '根据数据源类型，查询数据源列表', '1', 'L-213', '925050527715164160', '1',
        '/dataresource/getDatasourceListByType', '1', '3', NULL, NULL, '2021-12-29 12:42:46', NULL, NULL, NULL, NULL,
        '2021-12-29 12:42:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jodpiframe`.`sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`,
                                         `type`, `layout`, `order_by`, `remark`, `create_time`, `creator_id`,
                                         `creator_name`, `creator_depart_id`, `creator_depart_name`, `update_time`,
                                         `updater_id`, `updater_name`, `updater_depart_id`, `updater_depart_name`,
                                         `need_auth`)
VALUES ('925061126918504468', '根据数据源查看表', '1', 'L-214', '925050527715164160', '1',
        '/dataresource/getTableByDatasourceId', '1', '3', NULL, NULL, '2021-12-29 12:42:46', NULL, NULL, NULL, NULL,
        '2021-12-29 12:42:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jodpiframe`.`sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`,
                                         `type`, `layout`, `order_by`, `remark`, `create_time`, `creator_id`,
                                         `creator_name`, `creator_depart_id`, `creator_depart_name`, `update_time`,
                                         `updater_id`, `updater_name`, `updater_depart_id`, `updater_depart_name`,
                                         `need_auth`)
VALUES ('925050527715164161', '数据源管理', '0', 'L-212', '', '1', '/datasource/datasource/**', '1', '3', NULL, '',
        '2022-02-16 21:38:27', '', '', '', '', '2022-02-16 21:38:27', '', '', '', '', NULL);