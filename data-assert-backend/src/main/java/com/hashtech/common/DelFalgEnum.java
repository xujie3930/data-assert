package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public enum DelFalgEnum {
    HAS_DELETE(0, "Y"),
    NOT_DELETE(1, "N"),
    ;
    private Integer code;
    private String desc;

    DelFalgEnum() {
    }

    DelFalgEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DelFalgEnum find(Integer code) {
        for (DelFalgEnum value : DelFalgEnum.values()) {
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
