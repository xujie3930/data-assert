package com.hashtech.feign.request;

public class AppQueryListRequest {
    private String appGroupName;//应用分组名称
    private String appName;//应用名称
    private Integer certificationType;//应用认证方式
    private Integer isEnable;//应用启用状态
    private Integer period;//应用token有效期

    public String getAppGroupName() {
        return appGroupName;
    }

    public void setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(Integer certificationType) {
        this.certificationType = certificationType;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
