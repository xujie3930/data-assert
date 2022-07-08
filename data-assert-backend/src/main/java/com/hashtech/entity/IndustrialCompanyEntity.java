package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产业-企业表
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("industrial_company_relation")
public class IndustrialCompanyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 标签id
     */
    private String industrialId;

    /**
     * 企业id
     */
    private String companyInfoId;

    /**
     * 创建时间
     */
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
    private Boolean delFlag;

    public IndustrialCompanyEntity() {
    }

    public IndustrialCompanyEntity(String id, String industrialId, String companyInfoId, Date createTime, String createUserId, String createBy, Date updateTime, String updateUserId, String updateBy, Boolean delFlag) {
        this.id = id;
        this.industrialId = industrialId;
        this.companyInfoId = companyInfoId;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }

    public String getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(String companyInfoId) {
        this.companyInfoId = companyInfoId;
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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}
