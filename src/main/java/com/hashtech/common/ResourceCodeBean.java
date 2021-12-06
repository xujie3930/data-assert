package com.hashtech.common;

import com.hashtech.businessframework.system.code.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),

        RESOURCE_CODE_60000000("60000000", "名称不能多于20字"),
        RESOURCE_CODE_60000001("60000001", "描述不能多于200字"),
        RESOURCE_CODE_60000002("60000002", "主题名称不能重复"),
        RESOURCE_CODE_60000003("60000003", "资源名称不能重复"),
        RESOURCE_CODE_60000004("60000004", "主题名称不能为空"),
        RESOURCE_CODE_60000005("60000005", "资源名称不能为空"),

        RESOURCE_CODE_60000006("60000006", "资源表不存在"),
        RESOURCE_CODE_60000007("60000007", "资源不明确"),
        RESOURCE_CODE_60000008("60000008", "资源表不明确"),
        RESOURCE_CODE_60000009("60000009", "连接资源库失败"),
        RESOURCE_CODE_60000010("60000010", "该资源库下没有表，请更换资源库"),
        RESOURCE_CODE_60000011("60000011", "该url没有匹配到具体的资源表，请重新确认"),
        ;

        public final String code;
        public final String message;

        ResourceCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static ResourceCode find(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ResourceCode value : ResourceCode.values()) {
                    if (code.equals(value.getCode())) {
                        return value;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
