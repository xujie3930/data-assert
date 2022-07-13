package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public enum InterfaceTypeEnum {
    SAVE(0, "save"),
    UPDATE(1, "update"),
    ;
    private Integer code;
    private String desc;

    InterfaceTypeEnum() {
    }

    InterfaceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InterfaceTypeEnum find(Integer code) {
        for (InterfaceTypeEnum value : InterfaceTypeEnum.values()) {
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
