package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 主数据类别表
 * </p>
 *
 * @author xujie
 * @since 2022-03-23
 */
@EqualsAndHashCode(callSuper = false)
@TableName("master_data")
public class MasterDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主数据类别id
     */
    private Integer id;
    /**
     * 主数据类别描述
     */
    private String name;

    private String themeId;

    public MasterDataEntity() {
    }

    public MasterDataEntity(Integer id, String name, String themeId) {
        this.id = id;
        this.name = name;
        this.themeId = themeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
