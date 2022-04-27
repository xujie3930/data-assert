package com.hashtech.feign.request;

public class DatasourceApiParamSaveRequest {

    private String fieldName;
    private String fieldType;
    //和isRequest同值
    private Integer required;
    private String desc;
    //是否为请求参数【0--是，1--否】
    private Integer isRequest = 1;
    //是否为返回参数【0--是，1--否】
    private Integer isResponse = 1;

    public DatasourceApiParamSaveRequest() {
    }

    public DatasourceApiParamSaveRequest(String fieldName, String fieldType, Integer required, String desc, Integer isRequest, Integer isResponse) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.required = required;
        this.desc = desc;
        this.isRequest = isRequest;
        this.isResponse = isResponse;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
}
