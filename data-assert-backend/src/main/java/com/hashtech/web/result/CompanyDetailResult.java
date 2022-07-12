package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 企业详情返回
 * @create 2022-04-11 15:56
 **/
public class CompanyDetailResult {

    private String id;

    /**
     * 统一社会信用代码
     */
    private String uscc;

    /**
     * 组织机构名称
     */
    private String corpNm;

    //关联标签
    private List<String> tagMap;

    //关联标签数量
    private Integer tagNum;

    private List<String> industrialIds;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String updateBy;

    private String describe;

    public CompanyDetailResult() {
    }

    public CompanyDetailResult(String id, String uscc, String corpNm, List<String> tagMap, Integer tagNum, List<String> industrialIds, Date updateTime, String updateBy, String describe) {
        this.id = id;
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.tagMap = tagMap;
        this.tagNum = tagNum;
        this.industrialIds = industrialIds;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.describe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getTagMap() {
        return tagMap;
    }

    public void setTagMap(List<String> tagMap) {
        this.tagMap = tagMap;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getIndustrialIds() {
        return industrialIds;
    }

    public void setIndustrialIds(List<String> industrialIds) {
        this.industrialIds = industrialIds;
    }
}
