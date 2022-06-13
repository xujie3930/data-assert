package com.hashtech.feign.result;

public class AppAuthResult {

    private Integer tokenType;

    private Long periodBegin;

    private Long periodEnd;

    private Integer callCountType;//可调用类型，1：无限次，0：有限次

    private Integer allowCall;

    private Integer authEffectiveTime;//授权有效期，1：永久，0：短期

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
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

    public Integer getCallCountType() {
        return callCountType;
    }

    public void setCallCountType(Integer callCountType) {
        this.callCountType = callCountType;
    }

    public Integer getAllowCall() {
        return allowCall;
    }

    public void setAllowCall(Integer allowCall) {
        this.allowCall = allowCall;
    }

    public Integer getAuthEffectiveTime() {
        return authEffectiveTime;
    }

    public void setAuthEffectiveTime(Integer authEffectiveTime) {
        this.authEffectiveTime = authEffectiveTime;
    }
}
