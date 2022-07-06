package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
//@EqualsAndHashCode(callSuper = false)
@TableName("tag_category")
public class TagCategoryEntity implements Serializable {

    @TableId(type = IdType.ID_WORKER)
    @TableField("`id`")
    private Long id;//bigint(19) NOT NULL COMMENT 'ID',
    @TableField("`pid`")
    private Long pid;//bigint(19) NOT NULL COMMENT '父级ID，0代表顶层',
    @TableField("`level`")
    private Short level;//smallint(5) NOT NULL DEFAULT '1' COMMENT '分组层数',
    @TableField("`name`")
    private String name;//varchar(100) NOT NULL COMMENT '名称',
    @TableField("`describe`")
    private String describe;//varchar(1000) DEFAULT NULL COMMENT '描述',
    @TableField("`del_flag`")
    private Byte delFlag;//tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    @TableField("`create_by`")
    private String createBy;//varchar(100) DEFAULT NULL COMMENT '创建人',
    @TableField("`create_user_id`")
    private String createUserId;//varchar(100) DEFAULT NULL COMMENT '创建人',
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;//datetime(3) DEFAULT NULL COMMENT '更新时间',
    @TableField("`update_by`")
    private String updateBy;//varchar(100) DEFAULT NULL COMMENT '更新人',
    @TableField("`update_user_id`")
    private String updateUserId;//varchar(100) DEFAULT NULL COMMENT '更新人',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
