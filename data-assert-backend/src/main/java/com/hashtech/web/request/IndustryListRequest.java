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
    //企业代码
    private String uscc;
    //企业名称
    private String corpNm;
    //关联标签id
    private String tagId;

    public String getIndustrialName() {
        return industrialName;
    }

    public void setIndustrialName(String industrialName) {
        this.industrialName = industrialName;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getCorpNm() {
        return corpNm;
    }

    public void setCorpNm(String corpNm) {
        this.corpNm = corpNm;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}

