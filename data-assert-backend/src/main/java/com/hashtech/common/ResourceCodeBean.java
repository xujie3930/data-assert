package com.hashtech.common;

import com.hashtech.config.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        //数据服务错误码
        RESOURCE_CODE_20000001("20000001", "API Path不能为空，只能由字母组成，长度为16！"),
        RESOURCE_CODE_20000002("20000002", "API 名称不能为空，只能由字母、数字、下划线组成，长度不能超过50！"),
        RESOURCE_CODE_20000003("20000003", "API 名称已存在！"),
        RESOURCE_CODE_20000004("20000004", "请先勾选返回参数！"),
        RESOURCE_CODE_20000005("20000005", "API Path已存在！"),


        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),
        RESOURCE_CODE_10000003("10000003", "系统繁忙，请稍后再试"),


        RESOURCE_CODE_60000000("60000000", "主题名称最多为12字"),
        RESOURCE_CODE_60000001("60000001", "描述最多为200字"),
        RESOURCE_CODE_60000002("60000002", "主题名称重复，请重新输入"),
        RESOURCE_CODE_60000003("60000003", "资源分类名称重复，请重新输入"),
        RESOURCE_CODE_60000004("60000004", "主题名称不能为空"),
        RESOURCE_CODE_60000005("60000005", "资源分类名称不能为空"),

        RESOURCE_CODE_60000006("60000006", "资源表不存在"),
        RESOURCE_CODE_60000007("60000007", "资源不明确"),
        RESOURCE_CODE_60000008("60000008", "资源表不明确"),
        RESOURCE_CODE_60000009("60000009", "该资源表暂不可用，请联系管理员"),
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
        RESOURCE_CODE_60000022("60000022", "主题下不能添加资源表,请选择具体的资源"),
        RESOURCE_CODE_60000023("60000023", "该分类下资源表已重复，请选择其他资源表!"),
        RESOURCE_CODE_60000024("60000024", "资源只可以在主题下添加！"),
        RESOURCE_CODE_60000025("60000025", "资源表编号不能为空！"),
        RESOURCE_CODE_60000026("60000026", "资源表编号最多为50字！"),
        RESOURCE_CODE_60000027("60000027", "资源表编号不能重复！"),
        RESOURCE_CODE_60000028("60000028", "该名称已存在"),
        RESOURCE_CODE_60000029("60000029", "接口名称不能为空"),
        RESOURCE_CODE_60000030("60000030", "系统用户错误"),
        RESOURCE_CODE_60000031("60000031", "资源表已添加，不能重复添加"),
        RESOURCE_CODE_60000032("60000032", "表中文名称包含特殊字符，请重新输入！"),
        RESOURCE_CODE_60000033("60000033", "表中文名称不能重复！"),
        RESOURCE_CODE_60000034("60000034", "表中文名称不能为空！"),
        RESOURCE_CODE_60000035("60000035", "用户异常！"),
        RESOURCE_CODE_60000036("60000036", "获取资源库连接失败！"),
        RESOURCE_CODE_60000037("60000037", "数据源不明确，id为空！"),
        RESOURCE_CODE_60000038("60000038", "获取开放平台状态失败！"),
        RESOURCE_CODE_60000039("60000039", "数据服务异常！"),
        RESOURCE_CODE_60000040("60000040", "该资源表中已在开放平台开放，暂不能编辑！"),
        RESOURCE_CODE_60000041("60000041", "主数据不能删除！"),
        RESOURCE_CODE_60000042("60000042", "该数据源已停用，请重新选择!"),
        RESOURCE_CODE_60000043("60000043", "授权资源不存在!"),
        RESOURCE_CODE_60000044("60000044", "url不能为空"),
        RESOURCE_CODE_60000045("60000045", "请求方式不能为空"),
        RESOURCE_CODE_60000046("60000046", "支持格式不能为空"),
        RESOURCE_CODE_60000047("60000047", "标签分类名称不可为空"),
        RESOURCE_CODE_60000048("60000048", "系统全称最多为100字"),
        RESOURCE_CODE_60000049("60000049", "请求方式不允许修改！"),
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
