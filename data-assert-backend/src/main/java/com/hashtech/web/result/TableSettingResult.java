package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-12-1
 */
//@Data
@EqualsAndHashCode(callSuper = false)
public class TableSettingResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表的访问url
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
     * 入参信息
     */
    private List<Structure> paramInfo;

    /**
     * 出参信息
     */
    private List<Structure> outParamInfo;

    /**
     * 表结构
     */
    private List<Structure> structureList;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public TableSettingResult() {
    }

    public TableSettingResult(String requestUrl, Integer formats, Integer requestWay, String explainInfo, List<Structure> paramInfo, List<Structure> outParamInfo, List<Structure> structureList, String interfaceName, Date createTime, Date updateTime) {
        this.requestUrl = requestUrl;
        this.formats = formats;
        this.requestWay = requestWay;
        this.explainInfo = explainInfo;
        this.paramInfo = paramInfo;
        this.outParamInfo = outParamInfo;
        this.structureList = structureList;
        this.interfaceName = interfaceName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
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

    public List<Structure> getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(List<Structure> paramInfo) {
        this.paramInfo = paramInfo;
    }

    public List<Structure> getOutParamInfo() {
        return outParamInfo;
    }

    public void setOutParamInfo(List<Structure> outParamInfo) {
        this.outParamInfo = outParamInfo;
    }

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
