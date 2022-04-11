package com.hashtech.config.exception;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.exception.event
 * ClassName: SystemExceptionEvent
 * Description:  SystemExceptionEvent类
 * Date: 2021/5/31 3:35 下午
 *
 * @author liyanhui
 */
public class SystemExceptionEvent extends BaseExceptionEvent {
    /**
     * 创建一个新的事件
     *
     * @param source 事件发生时的对象
     */
    public SystemExceptionEvent(SystemException source) {
        super(source);
    }

    @Override
    public String defCode() {
        return "_SYSTEM_EXCEPTION";
    }
}
