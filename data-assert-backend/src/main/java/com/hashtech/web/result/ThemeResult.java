package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
//@Data
@EqualsAndHashCode(callSuper = false)
public class ThemeResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 主题或者资源分类名称
     */
    private String name;

    /**
     * 主题或者资源分类描述
     */
    private String descriptor;

    /**
     * 用于目录树排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 该主题下的资源列表
     */
    private List<ThemeResult> resourceList = new LinkedList<>();

    /**
     * 是否隐藏编辑按钮，true：隐藏，false：不隐藏
     */
    private Boolean hidden;

    /**
     * 创建者id
     */
    private String createUserId;

    /**
     * icron图标
     */
    private String picPath;

    /**
     * icron图标路径
     */
    private String picUrl;

    public ThemeResult() {
    }

    public ThemeResult(String id, String name, String descriptor, Integer sort, Date createTime, String createBy, Date updateTime, String updateBy, List<ThemeResult> resourceList, Boolean hidden, String createUserId, String picPath, String picUrl) {
        this.id = id;
        this.name = name;
        this.descriptor = descriptor;
        this.sort = sort;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.resourceList = resourceList;
        this.hidden = hidden;
        this.createUserId = createUserId;
        this.picPath = picPath;
        this.picUrl = picUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public List<ThemeResult> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ThemeResult> resourceList) {
        this.resourceList = resourceList;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
