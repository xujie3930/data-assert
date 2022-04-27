package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

/**
 * @author xujie
 * @description 根据表明获取源库信息
 * @create 2021-11-29 17:40
 **/
//@Data
public class ResourceTablePreposeRequest extends BusinessBasePageForm {
    /**
     * 数据源id
     */
    private String datasourceId;
    /**
     * 表名称
     */
    private String tableName;

    private String fields;

    private String desensitizeFields;

    public ResourceTablePreposeRequest() {
    }

    public ResourceTablePreposeRequest(String datasourceId, String tableName) {
        this.datasourceId = datasourceId;
        this.tableName = tableName;
    }

    public ResourceTablePreposeRequest(String datasourceId, String tableName, String fields, String desensitizeFields) {
        this.datasourceId = datasourceId;
        this.tableName = tableName;
        this.fields = fields;
        this.desensitizeFields = desensitizeFields;
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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getDesensitizeFields() {
        return desensitizeFields;
    }

    public void setDesensitizeFields(String desensitizeFields) {
        this.desensitizeFields = desensitizeFields;
    }
}
