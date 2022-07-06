package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签关联表
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
//@EqualsAndHashCode(callSuper = false)
@TableName("tag_category_relation")
public class TagCategoryRelationEntity implements Serializable {

    @TableId(type = IdType.ID_WORKER)
    @TableField("`id`")
    private Long id;//bigint(19) NOT NULL COMMENT 'ID',
    @TableField("`tag_category_id`")
    private Long tagCategoryId;//bigint(19) NOT NULL COMMENT '组ID',
    @TableField("`tag_id`")
    private String tagId;//varchar(100) NOT NULL COMMENT '标签ID',
    @TableField("`del_flag`")
    private Byte delFlag;//tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(Long tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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
}
