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

        RESOURCE_CODE_60000000("60000000", "主题名称最多为12字"),
        RESOURCE_CODE_60000001("60000001", "描述最多为200字"),
        RESOURCE_CODE_60000002("60000002", "主题名称重复，请重新输入"),
        RESOURCE_CODE_60000003("60000003", "资源分类名称重复，请重新输入"),
        RESOURCE_CODE_60000004("60000004", "主题名称不能为空"),
        RESOURCE_CODE_60000005("60000005", "资源分类名称不能为空"),

        RESOURCE_CODE_60000006("60000006", "资源表不存在"),
        RESOURCE_CODE_60000007("60000007", "资源不明确"),
        RESOURCE_CODE_60000008("60000008", "资源表不明确"),
        RESOURCE_CODE_60000009("60000009", "连接资源库失败"),
        RESOURCE_CODE_60000010("60000010", "该资源库下没有表，请更换资源库"),
        RESOURCE_CODE_60000011("60000011", "该url没有匹配到具体的资源表，请重新确认"),
        RESOURCE_CODE_60000012("60000012", "资源名称最多为20字"),
        RESOURCE_CODE_60000013("60000013", "删除的资源分类中包含已在开放平台中开放的资源表，暂不能删除"),
        RESOURCE_CODE_60000014("60000014", "删除的主题库中包含已在开放平台中开放的资源表，暂不能删除!"),
        RESOURCE_CODE_60000015("60000015", "二级目录不能拖动到一级目录"),
        RESOURCE_CODE_60000016("60000016", "一级目录不能拖动到二级目录"),
        RESOURCE_CODE_60000017("60000017", "该资源表已在开放平台中开放，暂不能删除！"),
        RESOURCE_CODE_60000018("60000018", "列表所选中的资源表中包含已在开放平台开放的资源表，暂不能删除，请重新选择！"),
        RESOURCE_CODE_60000019("60000019", "该主题已被删除，不能添加分类！"),
        RESOURCE_CODE_60000020("60000020", "资源表处于开放状态才能进行接口设置！"),
        RESOURCE_CODE_60000021("60000021", "该资源表尚未开放，不能查看数据"),
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
