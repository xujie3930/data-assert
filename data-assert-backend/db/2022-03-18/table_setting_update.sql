/*micro-oauth2-api项目*/
/*更新explainInfo字段长度*/
use daas;
alter table table_setting change explain_info explain_info varchar(2000) COMMENT '请求实例说明';
