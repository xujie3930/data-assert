package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源信息设置表
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("table_setting_apps")
public class TableSettingAppsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER)
    private Long id;//bigint(19) NOT NULL COMMENT '主键',
    private String appId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app id',
    private String appGroupId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app group id',
    private String tableSettingId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'table setting id',
    private Byte delFlag;//tinyint(1) DEFAULT NULL COMMENT '逻辑删除',
    private Date createTime;//datetime(6) DEFAULT NULL COMMENT '创建时间',
    private String createBy;//varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppGroupId() {
        return appGroupId;
    }

    public void setAppGroupId(String appGroupId) {
        this.appGroupId = appGroupId;
    }

    public String getTableSettingId() {
        return tableSettingId;
    }

    public void setTableSettingId(String tableSettingId) {
        this.tableSettingId = tableSettingId;
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
}
