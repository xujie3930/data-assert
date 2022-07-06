package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

/**
 * @author xujie
 * @description 标签列表
 * @create 2022-04-07 11:08
 **/

//@Data
public class TagListRequest extends BusinessBasePageForm {

    //标签名称
    private String name;

    //更新人
    private String updateBy;

    //是否启用：0-否，1-是
    private Integer state;

    //最近使用时间-范围
    private String lastUsedTimeBegin;

    //最近使用时间-范围
    private String lastUsedTimeEnd;

    private String sort = "desc";

    private String sortField;

    //分类名称
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public TagListRequest() {
    }

    public TagListRequest(String name, String updateBy, Integer state, String lastUsedTimeBegin, String lastUsedTimeEnd, String sort, String sortField) {
        this.name = name;
        this.updateBy = updateBy;
        this.state = state;
        this.lastUsedTimeBegin = lastUsedTimeBegin;
        this.lastUsedTimeEnd = lastUsedTimeEnd;
        this.sort = sort;
        this.sortField = sortField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLastUsedTimeBegin() {
        return lastUsedTimeBegin;
    }

    public void setLastUsedTimeBegin(String lastUsedTimeBegin) {
        this.lastUsedTimeBegin = lastUsedTimeBegin;
    }

    public String getLastUsedTimeEnd() {
        return lastUsedTimeEnd;
    }

    public void setLastUsedTimeEnd(String lastUsedTimeEnd) {
        this.lastUsedTimeEnd = lastUsedTimeEnd;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}

