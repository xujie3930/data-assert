package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.common.DelFalgEnum;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("resource_table")
public class ResourceTableEntity implements Serializable {

    public static final String NAME = "NAME";
    public static final String CHINESE_NAME = "CHINESE_NAME";
    public static final String RESOURCE_ID = "RESOURCE_ID";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String DEL_FLAG = "DEL_FLAG";
    public static final String UPDATE_TIME = "UPDATE_TIME";
    public static final String CREATE_TIME = "CREATE_TIME";
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER)
    private String id;
    /**
     * 资源id
     */
    private String resourceId;

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

    /**
     * 数据源id
     */
    private String datasourceId;

    public ResourceTableEntity() {
    }

    public ResourceTableEntity(String id, String resourceId, String name, String chineseName, String serialNum, String orgId, String orgName, String requestUrl, String source, Integer columnsCount, Long dataSize, String matters, String descriptor, Integer sort, Date createTime, String createBy, Date updateTime, String updateBy, String delFlag, String themeId, String createUserId, String datasourceId) {
        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.chineseName = chineseName;
        this.serialNum = serialNum;
        this.orgId = orgId;
        this.orgName = orgName;
        this.requestUrl = requestUrl;
        this.source = source;
        this.columnsCount = columnsCount;
        this.dataSize = dataSize;
        this.matters = matters;
        this.descriptor = descriptor;
        this.sort = sort;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
        this.themeId = themeId;
        this.createUserId = createUserId;
        this.datasourceId = datasourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(Integer columnsCount) {
        this.columnsCount = columnsCount;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public String getMatters() {
        return matters;
    }

    public void setMatters(String matters) {
        this.matters = matters;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }
}