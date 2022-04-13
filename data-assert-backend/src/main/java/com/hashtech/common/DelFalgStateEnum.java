package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2022-04-08
 */
public enum DelFalgStateEnum {
    NOT_DELETE(0, "N"),
    HAS_DELETE(1, "Y"),
    ;
    private Integer code;
    private String desc;

    DelFalgStateEnum() {
    }

    DelFalgStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DelFalgStateEnum find(Integer code) {
        for (DelFalgStateEnum value : DelFalgStateEnum.values()) {
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
