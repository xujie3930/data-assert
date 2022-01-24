package com.hashtech.common;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@Getter
public enum StatusEnum {
    ENABLE(0),
    DISABLE(1),
    ;
    private Integer code;

    StatusEnum() {
    }

    StatusEnum(Integer code) {
        this.code = code;
    }

    public static StatusEnum find(Integer code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }


}
