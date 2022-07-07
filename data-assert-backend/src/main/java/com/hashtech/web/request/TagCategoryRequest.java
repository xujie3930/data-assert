package com.hashtech.web.request;

import com.hashtech.common.DelFlagEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class TagCategoryRequest {


    private Long id;//bigint(19) NOT NULL COMMENT 'ID',
    private Long pid=0l;//bigint(19) NOT NULL COMMENT '父级ID，0代表顶层',
    private Short level=1;//smallint(5) NOT NULL DEFAULT '1' COMMENT '分组层数',
    @NotBlank(message = "60000047")
    @Length(max = 50, message = "70000002")
    private String name;//varchar(100) NOT NULL COMMENT '名称',
    private String describe;//varchar(1000) DEFAULT NULL COMMENT '描述',
    private Byte delFlag = DelFlagEnum.ENA_BLED.getCode().byteValue();//tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    private Date createTime;//datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    private String createBy;//varchar(100) DEFAULT NULL COMMENT '创建人',
    private String createUserId;//varchar(100) DEFAULT NULL COMMENT '更新人',
    private Date updateTime;//datetime(3) DEFAULT NULL COMMENT '更新时间',
    private String updateBy;//varchar(100) DEFAULT NULL COMMENT '更新人',
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
