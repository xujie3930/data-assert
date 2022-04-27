/*micro-oauth2-api项目*/
/*更新explainInfo字段长度*/
use daas;
ALTER TABLE resource_table
    DROP external_state;
