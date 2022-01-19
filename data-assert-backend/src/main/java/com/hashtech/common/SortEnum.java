package com.hashtech.common;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@Getter
public enum SortEnum {
    DESC(0, "desc"),
    ASC(1, "asc"),
    ;
    private Integer code;
    private String desc;

    SortEnum() {
    }

    SortEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SortEnum find(Integer code) {
        for (SortEnum value : SortEnum.values()) {
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
