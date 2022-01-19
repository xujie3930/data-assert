package com.hashtech.config.exception;

import org.springframework.context.ApplicationEvent;

/**
 * Spring事件广播抽象类
 *
 * @author LIYANHUI
 */
public abstract class BaseEvent
        extends ApplicationEvent {
    /**
     * 事件编码
     */
    private String code;

    /**
     * 创建一个新的事件
     *
     * @param source 事件发生时的对象
     */
    public BaseEvent(Object source) {
        super(source);
    }

    /**
     * 获取编码
     *
     * @return 编码
     */
    public String getCode() {
        if (code == null) {
            code = defCode();
        }

        return code;
    }

    /**
     * 子类定义各自的编码
     *
     * @return 子类定义的编码
     */
    public abstract String defCode();
}
