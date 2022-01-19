package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.interval
 * ClassName: FrameworkException
 * Description:  FrameworkException类
 * Date: 2021/5/31 3:27 下午
 *
 * @author liyanhui
 */
public class FrameworkException extends BaseException {

    public FrameworkException(String errorCode) {
        super(errorCode);
    }

    public FrameworkException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public FrameworkException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public FrameworkException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }

    @Override
    public ExceptionType defExceptionType() {
        return ExceptionType.FRAMEWORK_EXCEPTION;
    }

    @Override
    public String defShortName() {
        return "Framework Exception";
    }
}
