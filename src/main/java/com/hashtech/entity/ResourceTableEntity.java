package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.common.DelFalgEnum;
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

    public static final String NAME = "NAME";
    public static final String CHINESE_NAME = "CHINESE_NAME";
    public static final String RESOURCE_ID = "RESOURCE_ID";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String DEL_FLAG = "DEL_FLAG";
    public static final String UPDATE_TIME = "UPDATE_TIME";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String EXTERNAL_STATE = "EXTERNAL_STATE";

    /**
     * 主键id
     */
    private String id;
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 是否在开放平台开放：0-开放，1-不开放，默认1
     */
    private Integer externalState = 1;

    /**
     * 资源表名称
     */
    private String name;

    /**
     * 资源表中文名称
     */
    private String chineseName;

    /**
     * 资源表编号
     */
    private String serialNum;

    /**
     * 所属组织id
     */
    private String orgId;

    /**
     * 所属组织id
     */
    private String orgName;

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
    private Long dataSize;

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
    private String delFlag = DelFalgEnum.NOT_DELETE.getDesc();

    /**
     * 主题id（同一张表只能用在一个主题和分类中）
     */
    private String themeId;

    /**
     * 创建者id
     */
    private String createUserId;
}
