package com.hashtech.feign.result;

public class ApiParamSaveResult {

    private String id;

    private String fieldName;

    private String tableName;

    private String fieldType;

    private Integer required;

    private String desc;

    private Integer isRequest;

    private Integer isResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(Integer isRequest) {
        this.isRequest = isRequest;
    }

    public Integer getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(Integer isResponse) {
        this.isResponse = isResponse;
    }

    public ApiParamSaveResult() {
    }

    public ApiParamSaveResult(String id, String fieldName, String tableName, String fieldType, Integer required, String desc, Integer isRequest, Integer isResponse) {
        this.id = id;
        this.fieldName = fieldName;
        this.tableName = tableName;
        this.fieldType = fieldType;
        this.required = required;
        this.desc = desc;
        this.isRequest = isRequest;
        this.isResponse = isResponse;
    }
}
