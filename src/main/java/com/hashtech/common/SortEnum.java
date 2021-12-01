package com.hashtech.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */
@AllArgsConstructor
@Getter
public enum SortEnum {
    DESC(0, "desc"),
    ASC(1, "asc"),
    ;
    private Integer code;
    private String desc;

    public static SortEnum find(Integer code) {
        for (SortEnum value : SortEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
