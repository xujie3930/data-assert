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
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tag")
public class TagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DEL_FLAG = "del_flag";
    public static final String NAME = "name";
    public static final String UPDATE_BY = "update_by";
    public static final String STATE = "state";
    public static final String LAST_USED_TIME = "last_used_time";
    public static final String UPDATE_TIME = "update_time";

    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 标签代码:8位随机数，不为空且唯一
     */
    @TableField("`code`")
    private String code;

    /**
     * 标签名称,50字内具有唯一性不为空
     */
    @TableField("`name`")
    private String name;

    /**
     * 标签描述,200字内可以为空
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 是否启用：0-否，1-是
     */
    private Integer state;

    /**
     * 使用次数
     */
    private Integer usedTime;

    /**
     * 最近使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUsedTime;

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
     * 是否删除:0-否，1-删除
     */
    @TableLogic
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
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

    public TagEntity() {
    }

    public TagEntity(String id, String code, String name, String describe, Integer state, Integer usedTime, Date lastUsedTime, Date createTime, String createUserId, String createBy, Date updateTime, String updateUserId, String updateBy, Integer delFlag) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.describe = describe;
        this.state = state;
        this.usedTime = usedTime;
        this.lastUsedTime = lastUsedTime;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
    }
}
