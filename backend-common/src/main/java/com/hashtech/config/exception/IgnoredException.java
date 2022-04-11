package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.interval
 * ClassName: IgnoredException
 * Description:  IgnoredException类
 * Date: 2021/5/31 3:29 下午
 *
 * @author liyanhui
 */
public class IgnoredException extends BaseException {

    public IgnoredException(String errorCode) {
        super(errorCode);
    }

    public IgnoredException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public IgnoredException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public IgnoredException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }

    @Override
    public ExceptionType defExceptionType() {
        return ExceptionType.IGNORED_EXCEPTION;
    }

    @Override
    public String defShortName() {
        return "Ignored Exception";
    }
}
