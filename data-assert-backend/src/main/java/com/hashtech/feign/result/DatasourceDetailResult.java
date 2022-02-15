package com.hashtech.feign.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
public class DatasourceDetailResult implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 工作空间id
     */
    private String spaceId;
    /**
     * 类别
     */
    private Integer category;

    /**
     * 数据源类型：1-mysql，2-oracle，3-postgresql，4-sqlServer，5-MongoDB
     */
    private Integer type;

    /**
     * 数据源自定义名称
     */
    private String name;

    /**
     * 连接信息
     */
    private String uri;

    /**
     * 是否启用：0-启用，1-非启用
     */
    private Integer status;

    /**
     * 是否删除:N-否，Y-删除
     */
    private String delFlag;

    /**
     * 最后一次同步时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastSyncTime;

    /**
     * 最后一次同步状态：0-成功，1-失败,2未执行
     */
    private Integer lastSyncStatus;

    /**
     * 描述信息
     */
    private String descriptor;

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
     * 是否展示密码：0-隐藏，1-展示，默认0
     */
    private Integer showPassword;

    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Integer getLastSyncStatus() {
        return lastSyncStatus;
    }

    public void setLastSyncStatus(Integer lastSyncStatus) {
        this.lastSyncStatus = lastSyncStatus;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
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

    public Integer getShowPassword() {
        return showPassword;
    }

    public void setShowPassword(Integer showPassword) {
        this.showPassword = showPassword;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DatasourceDetailResult() {
    }

    public DatasourceDetailResult(String id, String spaceId, Integer category, Integer type, String name, String uri, Integer status, String delFlag, Date lastSyncTime, Integer lastSyncStatus, String descriptor, Date createTime, String createBy, Date updateTime, String updateBy, Integer showPassword, String remark) {
        this.id = id;
        this.spaceId = spaceId;
        this.category = category;
        this.type = type;
        this.name = name;
        this.uri = uri;
        this.status = status;
        this.delFlag = delFlag;
        this.lastSyncTime = lastSyncTime;
        this.lastSyncStatus = lastSyncStatus;
        this.descriptor = descriptor;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.showPassword = showPassword;
        this.remark = remark;
    }
}
