package com.hashtech.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {
    ENABLE(0),
    DISABLE(1),
    ;
    private Integer code;

    public static StatusEnum find(Integer code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
