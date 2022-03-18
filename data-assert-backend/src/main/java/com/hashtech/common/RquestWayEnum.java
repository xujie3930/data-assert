package com.hashtech.common;

/**
 * @author xujie
 * @since 2021-11-25
 */
//@AllArgsConstructor
//@Getter
public enum RquestWayEnum {
    POST(0, "post请求"),
    GET(1, "get请求"),
    ;
    private Integer code;
    private String desc;

    RquestWayEnum() {
    }

    RquestWayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RquestWayEnum find(Integer code) {
        for (RquestWayEnum value : RquestWayEnum.values()) {
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
