package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public enum OpenFalgEnum {
    OPEN(0, "开放"),
    NOT_OPEN(1, "不开放"),
    ;
    private Integer code;
    private String desc;

    OpenFalgEnum() {
    }

    OpenFalgEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OpenFalgEnum find(Integer code) {
        for (OpenFalgEnum value : OpenFalgEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
