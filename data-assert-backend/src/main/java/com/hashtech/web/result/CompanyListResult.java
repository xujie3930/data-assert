package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 企业列表返回
 * @create 2022-04-11 15:56
 **/
public class CompanyListResult {

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
    private List<Map<String, String>> tagMap;

    //产业id
    private String industrialId;

    //关联标签数量
    private Integer tagNum;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String updateBy;

    private String describe;

    public CompanyListResult() {
    }

    public CompanyListResult(String id, String uscc, String corpNm, List<Map<String, String>> tagMap, String industrialId, Integer tagNum, Date updateTime, String updateBy, String describe) {
        this.id = id;
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.tagMap = tagMap;
        this.industrialId = industrialId;
        this.tagNum = tagNum;
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

    public List<Map<String, String>> getTagMap() {
        return tagMap;
    }

    public void setTagMap(List<Map<String, String>> tagMap) {
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

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }
}
