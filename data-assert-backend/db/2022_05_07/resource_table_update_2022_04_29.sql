use daas;
ALTER TABLE master_data ADD `theme_id` varchar(32) DEFAULT NULL COMMENT '对应主题数据';
update theme_resource set name = '法人主数据'  where name = '法人主题' and del_flag = 'N';
update theme_resource set name = '自然人主数据'  where name = '自然人主题'and del_flag = 'N';
update master_data set theme_id = (SELECT id from theme_resource where name = '法人主数据' and del_flag = 'N') where id = 1;
update master_data set theme_id = (SELECT id from theme_resource where name = '自然人主数据' and del_flag = 'N') where id = 2;