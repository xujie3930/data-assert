/*micro-oauth2-api项目*/
/*更新explainInfo字段长度*/
use daas;
alter table table_setting
    change explain_info explain_info text COMMENT '请求实例说明';
alter table table_setting
    change resp_info resp_info text COMMENT '返回字段信息';
