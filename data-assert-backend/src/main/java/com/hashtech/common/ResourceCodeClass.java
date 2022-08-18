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
        RESOURCE_CODE_10000004("10000004", "新增失败"),
        RESOURCE_CODE_10000005("10000005", "删除失败"),
        RESOURCE_CODE_10000006("10000006", "修改失败"),


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
        RESOURCE_CODE_70000011("70000011", "统一社会信用代码最多为18位，只支持阿拉伯数字或大写英文！"),
        RESOURCE_CODE_70000012("70000012", "统一社会信用代码不能为空！"),
        RESOURCE_CODE_70000013("70000013", "企业名称不能为空！"),
        RESOURCE_CODE_70000014("70000014", "企业名称在50字以内！"),
        RESOURCE_CODE_70000015("70000015", "企业描述在200字以内！"),
        RESOURCE_CODE_70000016("70000016", "该产业库下已存在该企业信息！"),
        RESOURCE_CODE_70000017("70000017", "输入错误，请按照统一社会信用代码规则填写！"),
        RESOURCE_CODE_70000018("70000018", "模板下载失败！"),
        RESOURCE_CODE_70000019("70000019", "该标签已被使用，请取消后再进行停用操作！"),
        RESOURCE_CODE_70000020("70000020", "上传失败，请使用下载的模板进行编辑！"),
        RESOURCE_CODE_70000021("70000021", "上传失败，请选择svg格式图片！"),
        RESOURCE_CODE_70000022("70000022", "已存在相同名称的图片！"),
        RESOURCE_CODE_70000023("70000023", "产业库名称不能为空"),
        RESOURCE_CODE_70000024("70000024", "产业库名称不多于50字"),
        RESOURCE_CODE_70000025("70000025", "产业库描述不多于200字"),
        RESOURCE_CODE_70000026("70000026", "该名称已存在，请重新输入！"),
        RESOURCE_CODE_70000027("70000027", "产业库名称包含特殊字符，请重新输入！"),
        RESOURCE_CODE_70000028("70000028", "产业库不存在，请重新输入！"),
        RESOURCE_CODE_70000029("70000029", "企业信用代码重复，请重新输入！"),
        RESOURCE_CODE_70000030("70000030", "上传的分类图标只支持png、jpg/jpeg、bmp格式，并且文件小于2MB!"),
        RESOURCE_CODE_70000031("70000031", "上传文件格式不正确, 仅支持xls和xlsx格式!"),
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
