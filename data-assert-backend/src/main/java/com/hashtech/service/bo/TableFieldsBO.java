package com.hashtech.service.bo;

import java.util.List;

public class TableFieldsBO {

    private List<String> fieldChineseNameList;
    private String fields;

    public List<String> getFieldChineseNameList() {
        return fieldChineseNameList;
    }

    public void setFieldChineseNameList(List<String> fieldChineseNameList) {
        this.fieldChineseNameList = fieldChineseNameList;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}