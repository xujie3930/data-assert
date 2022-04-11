package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception
 * ClassName: ExceptionType
 * Description:  ExceptionType类
 * Date: 2021/5/31 2:22 下午
 *
 * @author liyanhui
 */
public enum ExceptionType {

    /**
     * 系统异常，指不可预估的异常
     */
    SYSTEM_EXCEPTION,
    /**
     * 普通业务异常
     */
    APP_EXCEPTION,
    /**
     * 框架异常
     */
    FRAMEWORK_EXCEPTION,
    /**
     * 可忽略异常
     */
    IGNORED_EXCEPTION,
    /**
     * 参数异常
     */
    PARAM_EXCEPTION,
    /**
     * 数据库操作异常
     */
    DATA_EXCEPTION

}
