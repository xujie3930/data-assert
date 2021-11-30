package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源信息设置表
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("table_setting")
public class TableSettingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 关联resource_table表的id
     */
    private String resourceTableId;

    /**
     * 访问表的URL
     */
    private String requestUrl;

    /**
     * 支持格式：0-JSON
     */
    private Integer formats = 0;

    /**
     * 请求方式：0-POST,1-GET
     */
    private Integer requestWay = 0;

    /**
     * 请求实例说明
     */
    private String explainInfo;

    /**
     * 参数信息
     */
    private String paramInfo;

    /**
     * 表字段信息，存储于hdfs中
     */
    private String columnsInfo;


}
