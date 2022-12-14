INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925050527715164160', '数据资源管理', 0, 'L-183', NULL, 1, '', 1, 3, NULL, NULL, '2021-12-27 07:40:32', NULL, NULL,
        NULL, NULL, '2021-12-27 07:40:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051195712602112', '资产目录(主题)', 0, 'L-184', '925050527715164160', 1, '', 1, 3, NULL, NULL,
        '2021-12-27 07:43:12', NULL, NULL, NULL, NULL, '2021-12-27 07:43:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051400717598720', '主题列表', 1, 'L-185', '925051195712602112', 1, '/*/theme/list', 1, 3, NULL, NULL,
        '2021-12-29 12:41:00', NULL, NULL, NULL, NULL, '2021-12-29 12:41:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051596805505024', '主题排序', 1, 'L-186', '925051195712602112', 1, '/*/theme/rearrangement', 1, 3, NULL, NULL,
        '2021-12-29 12:41:06', NULL, NULL, NULL, NULL, '2021-12-29 12:41:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051727026061312', '主题详情', 1, 'L-187', '925051195712602112', 1, '/*/theme/info/**', 1, 3, NULL, NULL,
        '2021-12-29 12:41:15', NULL, NULL, NULL, NULL, '2021-12-29 12:41:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051850200186880', '删除主题', 1, 'L-188', '925051195712602112', 1, '/*/theme/delete', 1, 3, NULL, NULL,
        '2021-12-29 12:41:21', NULL, NULL, NULL, NULL, '2021-12-29 12:41:21', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925051974410305536', '查看重复主题', 1, 'L-189', '925051195712602112', 1, '/*/theme/hasExist', 1, 3, NULL, NULL,
        '2021-12-29 12:41:30', NULL, NULL, NULL, NULL, '2021-12-29 12:41:30', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925052202446225408', '添加主题', 1, 'L-190', '925051195712602112', 1, '/*/theme/save', 1, 3, NULL, NULL,
        '2021-12-29 12:41:38', NULL, NULL, NULL, NULL, '2021-12-29 12:41:38', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925052419786670080', '编辑主题', 1, 'L-191', '925051195712602112', 1, '/*/theme/update', 1, 3, NULL, NULL,
        '2021-12-29 12:41:44', NULL, NULL, NULL, NULL, '2021-12-29 12:41:44', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925052739900145664', '资产目录（资源分类）', 0, 'L-192', '925050527715164160', 1, '', 1, 3, NULL, NULL,
        '2021-12-27 07:49:20', NULL, NULL, NULL, NULL, '2021-12-27 07:49:20', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925058502110478336', '删除资源分类', 1, 'L-193', '925052739900145664', 1, '/*/theme/resource/delete', 1, 3, NULL,
        NULL, '2021-12-29 12:41:50', NULL, NULL, NULL, NULL, '2021-12-29 12:41:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925058649695453184', '查看重复资源分类', 1, 'L-194', '925052739900145664', 1, '/*/theme/resource/hasExist', 1, 3, NULL,
        NULL, '2021-12-29 12:41:57', NULL, NULL, NULL, NULL, '2021-12-29 12:41:57', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925058935717625856', '添加资源分类', 1, 'L-195', '925052739900145664', 1, '/*/theme/resource/save', 1, 3, NULL, NULL,
        '2021-12-29 12:42:10', NULL, NULL, NULL, NULL, '2021-12-29 12:42:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059146720477184', '添加资源表前置（查看资源表列表）', 1, 'L-196', '925052739900145664', 1,
        '/*/resource/table/prepose/getTablaList', 1, 3, NULL, NULL, '2021-12-29 12:42:26', NULL, NULL, NULL, NULL,
        '2021-12-29 12:42:26', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059302496927744', '编辑资源分类', 1, 'L-197', '925052739900145664', 1, '/*/theme/resource/update', 1, 3, NULL,
        NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, '2021-12-29 12:42:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059480603852800', '资源分类信息', 1, 'L-198', '925052739900145664', 1, '/*/theme/resource/info/**', 1, 3, NULL,
        NULL, '2021-12-29 12:42:36', NULL, NULL, NULL, NULL, '2021-12-29 12:42:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925059616826458112', '资源表信息（列表）', 1, 'L-199', '925052739900145664', 1, '/*/resource/table/pageList', 1, 3,
        NULL, NULL, '2021-12-29 12:42:42', NULL, NULL, NULL, NULL, '2021-12-29 12:42:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925060842578247680', '资产目录（资源表）', 0, 'L-200', '925050527715164160', 1, '', 1, 3, NULL, NULL,
        '2021-12-27 08:21:32', NULL, NULL, NULL, NULL, '2021-12-27 08:21:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925061126918504448', '删除资源表', 1, 'L-201', '925060842578247680', 1, '/*/resource/table/delete', 1, 3, NULL,
        NULL, '2021-12-29 12:42:46', NULL, NULL, NULL, NULL, '2021-12-29 12:42:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925061248763035648', '查看资源表详情', 1, 'L-202', '925060842578247680', 1, '/*/resource/table/info', 1, 3, NULL,
        NULL, '2021-12-29 12:42:49', NULL, NULL, NULL, NULL, '2021-12-29 12:42:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925066907265007616', '查询数据来源', 1, 'L-203', '925060842578247680', 1, '/*/resource/table/getDataSource/**', 1, 3,
        NULL, NULL, '2021-12-29 12:42:54', NULL, NULL, NULL, NULL, '2021-12-29 12:42:54', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925067264825229312', '添加资源表', 1, 'L-204', '925060842578247680', 1, '/*/resource/table/save', 1, 3, NULL, NULL,
        '2021-12-29 12:42:58', NULL, NULL, NULL, NULL, '2021-12-29 12:42:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925067711497633792', '编辑资源表', 1, 'L-205', '925060842578247680', 1, '/*/resource/table/update', 1, 3, NULL,
        NULL, '2021-12-29 12:43:02', NULL, NULL, NULL, NULL, '2021-12-29 12:43:02', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068022387834880', '接口设置', 0, 'L-206', '925050527715164160', 1, '', 1, 3, NULL, NULL, '2021-12-27 08:50:03',
        NULL, NULL, NULL, NULL, '2021-12-27 08:50:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068307491454976', '更新', 1, 'L-207', '925068022387834880', 1, '/*/table/setting/update', 1, 3, NULL, NULL,
        '2021-12-29 12:43:06', NULL, NULL, NULL, NULL, '2021-12-29 12:43:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068479533416448', '基本信息', 1, 'L-208', '925068022387834880', 1, '/*/table/setting/info/**', 1, 3, NULL, NULL,
        '2021-12-29 12:43:10', NULL, NULL, NULL, NULL, '2021-12-29 12:43:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` (`sys_resource_id`, `name`, `is_leaf`, `code`, `parent_id`, `status`, `url`, `type`,
                            `layout`, `order_by`, `remark`, `create_time`, `creator_id`, `creator_name`,
                            `creator_depart_id`, `creator_depart_name`, `update_time`, `updater_id`, `updater_name`,
                            `updater_depart_id`, `updater_depart_name`, `need_auth`)
VALUES ('925068623448375296', '预览', 1, 'L-209', '925068022387834880', 1, '/*/table/setting/preview', 1, 3, NULL, NULL,
        '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, '2021-12-29 12:43:14', NULL, NULL, NULL, NULL, NULL);