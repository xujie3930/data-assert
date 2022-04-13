package com.hashtech.feign.request;

import java.util.List;

public class DatasourceApiSaveRequest {

    private String id;
    private Integer saveType;
    private Integer type;
    private String name;
    private String requestType;
    private String responseType;
    private String path;
    private String apiGroupId;
    private String desc;
    private Integer apiVersion;

    private DatasourceApiGenerateSaveRequest apiGenerateSaveRequest;
    private List<DatasourceApiParamSaveRequest> apiParamSaveRequestList;

    public DatasourceApiSaveRequest() {
    }

    public DatasourceApiSaveRequest(String id, Integer saveType, Integer type, String name, String requestType, String responseType, String path, String apiGroupId, String desc, Integer apiVersion, DatasourceApiGenerateSaveRequest apiGenerateSaveRequest, List<DatasourceApiParamSaveRequest> apiParamSaveRequestList) {
        this.id = id;
        this.saveType = saveType;
        this.type = type;
        this.name = name;
        this.requestType = requestType;
        this.responseType = responseType;
        this.path = path;
        this.apiGroupId = apiGroupId;
        this.desc = desc;
        this.apiVersion = apiVersion;
        this.apiGenerateSaveRequest = apiGenerateSaveRequest;
        this.apiParamSaveRequestList = apiParamSaveRequestList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSaveType() {
        return saveType;
    }

    public void setSaveType(Integer saveType) {
        this.saveType = saveType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(String apiGroupId) {
        this.apiGroupId = apiGroupId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(Integer apiVersion) {
        this.apiVersion = apiVersion;
    }

    public DatasourceApiGenerateSaveRequest getApiGenerateSaveRequest() {
        return apiGenerateSaveRequest;
    }

    public void setApiGenerateSaveRequest(DatasourceApiGenerateSaveRequest apiGenerateSaveRequest) {
        this.apiGenerateSaveRequest = apiGenerateSaveRequest;
    }

    public List<DatasourceApiParamSaveRequest> getApiParamSaveRequestList() {
        return apiParamSaveRequestList;
    }

    public void setApiParamSaveRequestList(List<DatasourceApiParamSaveRequest> apiParamSaveRequestList) {
        this.apiParamSaveRequestList = apiParamSaveRequestList;
    }
}
