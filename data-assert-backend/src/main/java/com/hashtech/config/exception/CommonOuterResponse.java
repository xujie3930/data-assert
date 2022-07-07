package com.hashtech.config.exception;

import com.alibaba.fastjson.JSON;

import java.util.UUID;

/**
 * 通用的对外接口响应体，所有接口响应均应当使用该对象或者继承该对象；所谓对外接口，是指各个子系统对外提供的接口，目前包括以下三类：
 * 1、子系统间的接口
 * 2、提供给前端页面的接口
 * 3、提供给商户系统的接口
 * 继承此类的Response对象，不用再显式设置nonceStr，不用再重载toString方法
 *
 * @author YangMeng
 */
@Deprecated
public class CommonOuterResponse {

    public static final String SUCCEE = "0000";

    /**
     * 0000表示成功，其他表示失败
     */
    protected String returnCode = SUCCEE;

    /**
     * 如果result非0000，则 errorMsg 为错误信息， result为0000，errorMsg为空
     */
    protected String returnMsg = "Success";

    protected String returnError=null;

    protected String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    private boolean success = true;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public String getReturnCode() {
        return returnCode;
    }


    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }


    public String getReturnMsg() {
        return returnMsg;
    }


    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }


    public String getNonceStr() {
        return nonceStr;
    }


    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public boolean isSuccess() {
        return returnCode.equalsIgnoreCase(SUCCEE) && success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnError() {
        return returnError;
    }

    public void setReturnError(String returnError) {
        this.returnError = returnError;
    }
}
