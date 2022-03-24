package com.hashtech.common;

import lombok.Getter;

/**
 * @author xujie
 * @since 2021-11-25
 */

@Getter
public enum MasterFlagEnum {
    YES(0, "是主数据"),
    NO(1, "不是主数据"),
    ;
    private Integer code;
    private String desc;

    MasterFlagEnum() {
    }

    MasterFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MasterFlagEnum find(Integer code) {
        for (MasterFlagEnum value : MasterFlagEnum.values()) {
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
