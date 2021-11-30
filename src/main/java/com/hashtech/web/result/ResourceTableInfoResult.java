package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class ResourceTableInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 表明
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
     * 数据量
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
     * 基础信息
     */
    public BaseInfo baseInfo;
    /**
     * 表结构
     */
    public List<Structure> structureList;
    /**
     * 采样数据
     */
    public List<Object> sampleList;
}
