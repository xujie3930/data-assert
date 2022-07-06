package com.hashtech.web.result;

import java.util.Date;

public class TagCategoryRelationResult {

    private Long id;//bigint(19) NOT NULL COMMENT 'ID',
    private Long tagCategoryId;//bigint(19) NOT NULL COMMENT '组ID',
    private String tagId;//varchar(100) NOT NULL COMMENT '标签ID',
    private Byte delFlag;//tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
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
