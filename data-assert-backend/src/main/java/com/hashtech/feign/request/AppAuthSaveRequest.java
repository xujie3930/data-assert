package com.hashtech.feign.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xujie
 * @description 应用授权API,保存参数
 * @create 2022-02-25 15:08
 **/
public class AppAuthSaveRequest {
    //应用主键id
    @NotBlank(message = "20000021")
    private String appId;
    //API的主键id
    private List<String> apiId;
    //有效起始时间(-1表示无穷)
    private Long periodBegin;
    //有效结束时间(-1表示无穷)
    private Long periodEnd;
    //允许调用次数(-1表示无穷)
    private Integer allowCall;
    private String durationType;//可调用次数类型，0--有限次，1--无限次

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<String> getApiId() {
        return apiId;
    }

    public void setApiId(List<String> apiId) {
        this.apiId = apiId;
    }

    public Long getPeriodBegin() {
        return periodBegin;
    }

    public void setPeriodBegin(Long periodBegin) {
        this.periodBegin = periodBegin;
    }

    public Long getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Long periodEnd) {
        this.periodEnd = periodEnd;
    }

    public Integer getAllowCall() {
        return allowCall;
    }

    public void setAllowCall(Integer allowCall) {
        this.allowCall = allowCall;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }
}
