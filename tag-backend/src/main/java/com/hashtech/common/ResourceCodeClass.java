package com.hashtech.common;

import com.hashtech.config.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeClass {

    public enum ResourceCode {
        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),
        RESOURCE_CODE_10000003("10000003", "系统繁忙，请稍后再试"),


        RESOURCE_CODE_70000000("70000000", "标签代码最多为8位数字"),
        RESOURCE_CODE_70000001("70000001", "描述最多为200字"),
        RESOURCE_CODE_70000002("70000002", "标签名称最多为50字，不包含特殊字符"),
        RESOURCE_CODE_70000003("70000003", "标签代码重复，请重新输入"),
        RESOURCE_CODE_70000004("70000004", "标签名称重复，请重新输入"),
        RESOURCE_CODE_70000005("70000005", "标签代码不能为空"),
        RESOURCE_CODE_70000006("70000006", "标签名称不能为空"),
        RESOURCE_CODE_70000007("70000007", "标签异常！"),
        RESOURCE_CODE_70000008("70000008", "输入包含特殊字符！"),
        RESOURCE_CODE_70000009("70000009", "标签未停用，暂不能删除！"),
        RESOURCE_CODE_70000010("70000010", "批量选中的标签中含有启用状态的标签，咱不能删除！"),
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
