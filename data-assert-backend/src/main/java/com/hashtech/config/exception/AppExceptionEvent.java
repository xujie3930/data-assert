package com.hashtech.config.exception;

import com.hashtech.common.AppException;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.event
 * ClassName: AppExceptionEvent
 * Description:  AppExceptionEvent类
 * Date: 2021/5/31 3:34 下午
 *
 * @author liyanhui
 */
public class AppExceptionEvent extends BaseExceptionEvent {

    /**
     * 创建一个新的事件
     *
     * @param source 事件发生时的对象
     */
    public AppExceptionEvent(AppException source) {
        super(source);
    }

    @Override
    public String defCode() {
        return "_APP_EXCEPTION";
    }
}
