package com.hashtech.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于将系统返回码加载到redis中
 *
 * @author lidab
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface SystemCode {
    /**
     * 这个参数填写的是枚举的全限定名
     * @return
     */
    /*public String qualifiedName();*/
}
