package com.hashtech.common;

/**
 * @author xujie
 * @description 状态枚举
 * @create 2022-02-15 16:35
 **/
public enum DelFlagEnum {
    ENA_BLED(0, "未删除"),
    DIS_ABLED(1, "已删除"),
    ;

    private Integer code;
    private String desc;

    DelFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
