/*micro-oauth2-api项目*/
/*更新explainInfo字段长度*/
use daas;
alter table `resource_table`
    add column master_data_flag tinyint(1) DEFAULT '1' COMMENT '是否主数据：0-是，1-否，默认1';
alter table `resource_table`
    add column master_data_id int(11) DEFAULT '0' COMMENT '主数据id，对应master_data表的id';
