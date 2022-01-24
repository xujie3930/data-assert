package com.hashtech.common;


import java.util.HashMap;
import java.util.Map;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = -8610734771461037783L;
    private final String errorCode;
    private String errorMsg;
    private ExceptionParamBuilder builder;

    public AppException(ISystemCode sysCode) {
        super(sysCode.getCode());
        this.errorCode = sysCode.getCode();
        this.errorMsg = sysCode.getMessage();
        if (this.errorMsg.length() > 256) {
            this.errorMsg = this.errorMsg.substring(0, 255);
        }

    }

    public AppException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public AppException(Throwable cause) {
        super(cause);
        this.errorCode = "";
    }

    public AppException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        if (errorMsg.length() > 256) {
            errorMsg = errorMsg.substring(0, 255);
        }

        this.errorMsg = errorMsg;
    }

    public AppException(String errorCode, ExceptionParamBuilder builder) {
        super(errorCode);
        this.errorCode = errorCode;
        if (builder == null) {
            throw new IllegalArgumentException("builder must not be null");
        } else {
            this.builder = builder;
        }
    }

    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        if (errorMsg.length() > 256) {
            errorMsg = errorMsg.substring(0, 255);
        }

        this.errorMsg = errorMsg;
    }

    public Map<String, Object> getAll() {
        return (Map) (this.builder == null ? new HashMap() : this.builder.getAll());
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
