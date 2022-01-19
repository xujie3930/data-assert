package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("table_setting")
public class TableSettingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER)
    private String id;

    /**
     * 关联resource_table表的id
     */
    private String resourceTableId;

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

    /**
     * 接口名称
     */
    private String interfaceName;

    public TableSettingEntity() {
    }

    public TableSettingEntity(String id, String resourceTableId, Integer formats, Integer requestWay, String explainInfo, String paramInfo, String columnsInfo, String interfaceName) {
        this.id = id;
        this.resourceTableId = resourceTableId;
        this.formats = formats;
        this.requestWay = requestWay;
        this.explainInfo = explainInfo;
        this.paramInfo = paramInfo;
        this.columnsInfo = columnsInfo;
        this.interfaceName = interfaceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceTableId() {
        return resourceTableId;
    }

    public void setResourceTableId(String resourceTableId) {
        this.resourceTableId = resourceTableId;
    }

    public Integer getFormats() {
        return formats;
    }

    public void setFormats(Integer formats) {
        this.formats = formats;
    }

    public Integer getRequestWay() {
        return requestWay;
    }

    public void setRequestWay(Integer requestWay) {
        this.requestWay = requestWay;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }

    public String getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(String paramInfo) {
        this.paramInfo = paramInfo;
    }

    public String getColumnsInfo() {
        return columnsInfo;
    }

    public void setColumnsInfo(String columnsInfo) {
        this.columnsInfo = columnsInfo;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
