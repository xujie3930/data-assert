package com.hashtech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SystemCodeService implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(SystemCodeService.class);

    private static final Map<String, String> SYSTEM_CODE_MAP = new HashMap<>();

    @Deprecated
    public String getMessage(String code) {
        return SYSTEM_CODE_MAP.get(code);
    }

    public Optional<String> getMessageOptional(String code) {
        return Optional.ofNullable(getMessage(code));
    }

    public String getMessage(String code, Map<String, Object> params) {
        String message = getMessage(code);
        if (params != null) {
            StringBuilder key;
            for (String param : params.keySet()) {
                key = new StringBuilder("{" + param + "}");
                message = StringUtils.replace(message, key.toString(), (String) params.get(param));
            }
        }
        return message;
    }

    public Map<String, String> getSystemCodeMap() {
        return Collections.unmodifiableMap(SYSTEM_CODE_MAP);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class clazz = bean.getClass();
            Annotation systemCode = clazz.getAnnotation(SystemCode.class);
            if (systemCode != null) {
                Class[] classes = clazz.getClasses();
                if (null != classes) {
                    for (Class clazz0 : classes) {
                        Method getCode = clazz0.getMethod("getCode");
                        Method getMessage = clazz0.getMethod("getMessage");
                        Object[] objects = clazz0.getEnumConstants();
                        if (getCode != null && getMessage != null && objects != null) {
                            for (Object object : objects) {
                                String code = (String) getCode.invoke(object);
                                String message = (String) getMessage.invoke(object);
//                                log.info("将" + clazz0.getCanonicalName() + "的枚举放入内存,code:" + code + ",message:" + message);
                                SYSTEM_CODE_MAP.put(code, message);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("将Exception编码缓存至内存处理异常", e);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
