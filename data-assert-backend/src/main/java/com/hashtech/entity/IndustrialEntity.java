package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产业库
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("industrial")
public class IndustrialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DEL_FLAG = "del_flag";
    public static final String CREATE_TIME = "create_time";
    public static final String NAME = "name";
    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 产业库名称
     */
    private String name;

    /**
     * 上级目录id
     */
    private String parentId;

    /**
     * 描述
     */
    @TableField("`desc`")
    private String desc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人id
     */
    private String createUserId;

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
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除：0-否，1-删除
     */
    @TableLogic
    private Integer delFlag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public IndustrialEntity() {
    }

    public IndustrialEntity(String id, String name, String parentId, String desc, Date createTime, String createUserId, String createBy, Date updateTime, String updateUserId, String updateBy, Integer delFlag) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.desc = desc;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
    }
}
