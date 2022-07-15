package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

/**
 * @author xujie
 * @description 企业列表
 * @create 2022-04-07 11:08
 **/

//@Data
public class CompanyListRequest extends BusinessBasePageForm {

    //企业代码
    private String uscc;

    //企业名称
    private String corpNm;

    //关联标签id
    private String tagId;

    private String tagNum;

    private String updateTime;

    private String industrialName;

    private String industrialId;

    private Integer pageIndex;

    public CompanyListRequest() {
    }

    public CompanyListRequest(String uscc, String corpNm, String tagId, String tagNum, String updateTime, String industrialName, String industrialId, Integer pageIndex) {
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.tagId = tagId;
        this.tagNum = tagNum;
        this.updateTime = updateTime;
        this.industrialName = industrialName;
        this.industrialId = industrialId;
        this.pageIndex = pageIndex;
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

    public String getTagNum() {
        return tagNum;
    }

    public void setTagNum(String tagNum) {
        this.tagNum = tagNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIndustrialName() {
        return industrialName;
    }

    public void setIndustrialName(String industrialName) {
        this.industrialName = industrialName;
    }

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}

