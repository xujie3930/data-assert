package com.hashtech.common;

import java.io.Serializable;
import java.util.UUID;

public class BusinessResult<T> implements Serializable {
    private static final long serialVersionUID = 9191892693219217387L;
    private static final String RESP_CODE_SUCCESS = "0000";
    private static final String RESP_MSG_SUCCESS = "Success";
    private String returnCode = "0000";
    private String returnMsg = "Success";
    private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
    private boolean success;
    private T data;

    private BusinessResult() {
    }

    public static <T> BusinessResult<T> success(T data) {
        BusinessResult<T> result = new BusinessResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> BusinessResult<T> fail(String code, String message, T data) {
        BusinessResult<T> result = new BusinessResult();
        result.setReturnCode(code);
        result.setData(data);
        result.setReturnMsg(message);
        result.setSuccess(false);
        return result;
    }

    public static <T> BusinessResult<T> fail(String code, String message) {
        BusinessResult<T> result = new BusinessResult();
        result.setReturnCode(code);
        result.setReturnMsg(message);
        result.setSuccess(false);
        result.setData(null);
        return result;
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getNonceStr() {
        return this.nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}