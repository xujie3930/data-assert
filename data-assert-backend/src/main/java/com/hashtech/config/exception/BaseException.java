package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.interval
 * ClassName: BaseException
 * Description:  BaseException类
 * Date: 2021/5/31 2:17 下午
 *
 * @author liyanhui
 */
public abstract class BaseException extends RuntimeException {

    protected String errorCode;

    protected String errorMsg;

    public BaseException(String errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        if (errorMsg.length() > 256)
            errorMsg = errorMsg.substring(0, 255);
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        if (errorMsg.length() > 256)
            errorMsg = errorMsg.substring(0, 255);
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public abstract ExceptionType defExceptionType();

    public abstract String defShortName();

    @Override
    public String getMessage() {
        return this.errorMsg;
    }
}


