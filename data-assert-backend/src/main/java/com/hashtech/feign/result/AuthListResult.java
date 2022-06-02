package com.hashtech.feign.result;

public class AuthListResult {

    private String authId;
    private String apiName;
    private String apiPath;
    private String apiId;
    private String appId;
    private String appGroupId;
    private Long authPeriodBegin;
    private Long authPeroidEnd;
    private Integer authAllowCall;
    private Integer appTokenType;

    public String getAppGroupId() {
        return appGroupId;
    }

    public void setAppGroupId(String appGroupId) {
        this.appGroupId = appGroupId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getAuthPeriodBegin() {
        return authPeriodBegin;
    }

    public void setAuthPeriodBegin(Long authPeriodBegin) {
        this.authPeriodBegin = authPeriodBegin;
    }

    public Long getAuthPeroidEnd() {
        return authPeroidEnd;
    }

    public void setAuthPeroidEnd(Long authPeroidEnd) {
        this.authPeroidEnd = authPeroidEnd;
    }

    public Integer getAuthAllowCall() {
        return authAllowCall;
    }

    public void setAuthAllowCall(Integer authAllowCall) {
        this.authAllowCall = authAllowCall;
    }

    public Integer getAppTokenType() {
        return appTokenType;
    }

    public void setAppTokenType(Integer appTokenType) {
        this.appTokenType = appTokenType;
    }
}
