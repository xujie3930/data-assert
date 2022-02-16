package com.hashtech.web.result;

/**
 * @author xujie
 * @description
 * @create 2021-11-29 18:58
 **/
//@Data
public class Structure {

    //字段英文名称
    private String fieldEnglishName;
    //字段中文名称
    private String fieldChineseName;
    //表英文名称
    private String tableEnglishName;
    //表中文名称
    private String tableChineseName;
    //字段类型
    private String type;
    //是否必填,用于开放平台，作废
    private Boolean required = true;
    //是否脱敏：0-否，1-是（2022-02-15新增）
    private Integer desensitize;
    //是否为返回字段：0-否，1-是
    private Integer resParam;
    //是否为请求参数：0-否，1-是
    private Integer reqParam;


    public Structure() {
    }

    public Structure(String fieldEnglishName, String fieldChineseName, String tableEnglishName, String tableChineseName, String type, Boolean required, Integer desensitize) {
        this.fieldEnglishName = fieldEnglishName;
        this.fieldChineseName = fieldChineseName;
        this.tableEnglishName = tableEnglishName;
        this.tableChineseName = tableChineseName;
        this.type = type;
        this.required = required;
        this.desensitize = desensitize;
    }

    public String getFieldEnglishName() {
        return fieldEnglishName;
    }

    public void setFieldEnglishName(String fieldEnglishName) {
        this.fieldEnglishName = fieldEnglishName;
    }

    public String getFieldChineseName() {
        return fieldChineseName;
    }

    public void setFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
    }

    public String getTableEnglishName() {
        return tableEnglishName;
    }

    public void setTableEnglishName(String tableEnglishName) {
        this.tableEnglishName = tableEnglishName;
    }

    public String getTableChineseName() {
        return tableChineseName;
    }

    public void setTableChineseName(String tableChineseName) {
        this.tableChineseName = tableChineseName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getDesensitize() {
        return desensitize;
    }

    public void setDesensitize(Integer desensitize) {
        this.desensitize = desensitize;
    }

    public Integer getResParam() {
        return resParam;
    }

    public void setResParam(Integer resParam) {
        this.resParam = resParam;
    }

    public Integer getReqParam() {
        return reqParam;
    }

    public void setReqParam(Integer reqParam) {
        this.reqParam = reqParam;
    }
}
