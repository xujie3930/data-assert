package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("resource_table")
public class ResourceTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATE = "STATE";
    public static final String NAME = "NAME";
    public static final String RESOURCE_ID = "RESOURCE_ID";
    public static final String CREATE_BY = "RESOURCE_ID";
    public static final String DEL_FLAG = "DEL_FLAG";
    public static final String UPDATE_TIME = "UPDATE_TIME";

    /**
     * 主键id
     */
    private String id;
    /**
     * 关联theme_resource表的id
     */
    private String resourceId;

    /**
     * 0-开放，1-不开放
     */
    private Integer state;

    /**
     * 资源分类名称
     */
    private String name;

    /**
     * 表的访问url
     */
    private String requestUrl;

    /**
     * 当前表数据来源
     */
    private String source;

    /**
     * 当前表的字段数
     */
    private Integer columnsCount = 0;

    /**
     * 表数据量
     */
    private Integer dataSize;

    /**
     * 注意事项
     */
    private String matters;

    /**
     * 资源分类描述
     */
    private String descriptor;

    /**
     * 用于排序的字段
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
     * 更新人
     */
    private String updateBy;

    /**
     * 删除标识
     */
    private String delFlag = "N";
}
