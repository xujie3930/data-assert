use daas;
ALTER TABLE master_data ADD `theme_id` varchar(32) DEFAULT NULL COMMENT '对应主题数据';
update theme_resource set name = '法人主数据'  where name = '法人主题' and del_flag = 'N';
update theme_resource set name = '自然人主数据'  where name = '自然人主题'and del_flag = 'N';
update master_data set theme_id = (SELECT id from theme_resource where name = '法人主数据' and del_flag = 'N') where id = 1;
update master_data set theme_id = (SELECT id from theme_resource where name = '自然人主数据' and del_flag = 'N') where id = 2;
-- 其余主题下，更新主数据类型为否
update resource_table set master_data_flag =1,master_data_id = 0 where theme_id not in (select theme_id from master_data);
-- 主数据类型的主题，更新其主数据id
update resource_table set master_data_flag =0,master_data_id = 1 where theme_id = (SELECT theme_id from master_data where id =1);
update resource_table set master_data_flag =0,master_data_id = 2 where theme_id = (SELECT theme_id from master_data where id =2);