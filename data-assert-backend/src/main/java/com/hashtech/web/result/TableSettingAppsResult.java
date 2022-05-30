package com.hashtech.web.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-12-1
 */
//@Data
@EqualsAndHashCode(callSuper = false)
public class TableSettingAppsResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER)
    private Long id;//bigint(19) NOT NULL COMMENT '主键',
    @NotBlank
    private String appId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app id',
    @NotBlank
    private String appGroupId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app group id',
    private String tableSettingId;//varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'table setting id',

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
}
