package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2022-04-08
 */
@Getter
public enum TagStateEnum {
    DISABLE(0, "停用"),
    ENABLE(1, "启用"),
    ;
    private Integer code;
    private String desc;

    TagStateEnum() {
    }

    TagStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TagStateEnum find(Integer code) {
        for (TagStateEnum value : TagStateEnum.values()) {
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
