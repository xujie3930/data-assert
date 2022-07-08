package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

/**
 * @author xujie
 * @description 产业库列表
 * @create 2022-07-08 11:08
 **/

//@Data
public class IndustryListRequest extends BusinessBasePageForm {
    //产业库名称
    private String industrialName;

    public String getIndustrialName() {
        return industrialName;
    }

    public void setIndustrialName(String industrialName) {
        this.industrialName = industrialName;
    }
}

