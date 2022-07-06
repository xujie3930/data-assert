package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存公司信息
 * @create 2022-04-07 11:08
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

//@Data
public class CompanySaveRequest {

    @NotBlank(message = "70000012")
    @Length(max = 18, message = "70000011")
    private String uscc;

    @NotBlank(message = "70000013")
    @Length(max = 50, message = "70000014")
    private String corpNm;

    //企业标签id列表
    private List<String> tagIds;

    //企业描述
    @Length(max = 200, message = "70000015")
    private String describe;

    //产业库id列表
    private List<String> industrialIds;

    public CompanySaveRequest() {
    }

    public CompanySaveRequest(String uscc, String corpNm, List<String> tagIds, String describe, List<String> industrialIds) {
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.tagIds = tagIds;
        this.describe = describe;
        this.industrialIds = industrialIds;
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

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
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

