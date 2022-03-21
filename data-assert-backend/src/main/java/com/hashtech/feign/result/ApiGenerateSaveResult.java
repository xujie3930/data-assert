package com.hashtech.feign.result;

public class ApiGenerateSaveResult {

    private String id;

    private Integer model;

    private String sql;

    private String datasourceId;

    private String tableName;

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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ApiGenerateSaveResult() {
    }

    public ApiGenerateSaveResult(String id, Integer model, String sql, String datasourceId, String tableName) {
        this.id = id;
        this.model = model;
        this.sql = sql;
        this.datasourceId = datasourceId;
        this.tableName = tableName;
    }
}
