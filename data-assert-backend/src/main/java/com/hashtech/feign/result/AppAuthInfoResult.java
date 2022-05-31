package com.hashtech.feign.result;

import java.util.List;

public class AppAuthInfoResult {

    private String appId;
    private String appName;
    private List<ApiCascadeInfoResult> apiCascadeInfoStrList;
    private List<ApiCascadeInfoResult> noApiCascadeInfoStrList;
    private AppAuthResult authResult;
    private Integer infoType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<ApiCascadeInfoResult> getApiCascadeInfoStrList() {
        return apiCascadeInfoStrList;
    }

    public void setApiCascadeInfoStrList(List<ApiCascadeInfoResult> apiCascadeInfoStrList) {
        this.apiCascadeInfoStrList = apiCascadeInfoStrList;
    }

    public List<ApiCascadeInfoResult> getNoApiCascadeInfoStrList() {
        return noApiCascadeInfoStrList;
    }

    public void setNoApiCascadeInfoStrList(List<ApiCascadeInfoResult> noApiCascadeInfoStrList) {
        this.noApiCascadeInfoStrList = noApiCascadeInfoStrList;
    }

    public AppAuthResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult(AppAuthResult authResult) {
        this.authResult = authResult;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }
}
