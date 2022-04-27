package com.hashtech.feign.request;

public class DatasourceApiGenerateSaveRequest {

    private String id;
    private Integer model;
    private String datasourceId;
    private String sql;
    private String tableName;
    private Integer databaseType;//数据库类型:1-mysql,2-oracle,3-pg

    public DatasourceApiGenerateSaveRequest() {
    }

    public DatasourceApiGenerateSaveRequest(String id, Integer model, String datasourceId, String sql, String tableName, Integer databaseType) {
        this.id = id;
        this.model = model;
        this.datasourceId = datasourceId;
        this.sql = sql;
        this.tableName = tableName;
        this.databaseType = databaseType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(Integer databaseType) {
        this.databaseType = databaseType;
    }
}
