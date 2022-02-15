package com.hashtech.web.request;

/**
 * @author xujie
 * @description id不为空，为详情接口
 * tableName不为空，为添加表的前置接口
 * @create 2021-11-24 10:01
 **/
//@Data
public class ResourceTableNameRequest {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据源id
     */
    private String datasourceId;

    public ResourceTableNameRequest() {
    }

    public ResourceTableNameRequest(String tableName, String datasourceId) {
        this.tableName = tableName;
        this.datasourceId = datasourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }
}
