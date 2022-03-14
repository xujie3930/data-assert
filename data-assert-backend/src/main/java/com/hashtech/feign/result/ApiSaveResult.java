package com.hashtech.feign.result;

import java.util.List;

public class ApiSaveResult {

    private String id;
    private ApiGenerateSaveResult apiGenerateSaveRequest;
    private List<ApiParamSaveResult> apiParamSaveRequestList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ApiGenerateSaveResult getApiGenerateSaveRequest() {
        return apiGenerateSaveRequest;
    }

    public void setApiGenerateSaveRequest(ApiGenerateSaveResult apiGenerateSaveRequest) {
        this.apiGenerateSaveRequest = apiGenerateSaveRequest;
    }

    public List<ApiParamSaveResult> getApiParamSaveRequestList() {
        return apiParamSaveRequestList;
    }

    public void setApiParamSaveRequestList(List<ApiParamSaveResult> apiParamSaveRequestList) {
        this.apiParamSaveRequestList = apiParamSaveRequestList;
    }

    public ApiSaveResult() {
    }

    public ApiSaveResult(String id, ApiGenerateSaveResult apiGenerateSaveRequest, List<ApiParamSaveResult> apiParamSaveRequestList) {
        this.id = id;
        this.apiGenerateSaveRequest = apiGenerateSaveRequest;
        this.apiParamSaveRequestList = apiParamSaveRequestList;
    }
}
