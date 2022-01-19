package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.interval
 * ClassName: SystemException
 * Description:  SystemException类
 * Date: 2021/5/31 3:27 下午
 *
 * @author liyanhui
 */
public class SystemException extends BaseException {


    public SystemException(String errorCode) {
        super(errorCode);
    }

    public SystemException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public SystemException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public SystemException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }

    @Override
    public ExceptionType defExceptionType() {
        return ExceptionType.SYSTEM_EXCEPTION;
    }

    @Override
    public String defShortName() {
        return "System Exception";
    }
}
