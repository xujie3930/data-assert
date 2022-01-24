package com.hashtech.web.result;

/**
 * @author xujie
 * @description
 * @create 2021-11-29 18:57
 **/
//@Data
public class BaseInfo {
    /**
     * 主键id
     */
    private String id;
    /**
     * 表名称,自动识别
     */
    private String name;

    /**
     * 表中文名称
     */
    private String chineseName;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer externalState = 1;
    /**
     * 资源分类列表展示的列表的总字段，自动识别
     */
    private Integer columnsCount;
    /**
     * 数据总条数，自动识别
     */
    private Long dataSize;
    /**
     * 数据来源
     */
    private String source;
    /**
     * 表描述信息
     */
    private String descriptor;
    /**
     * 注意事项
     */
    private String matters;

    /**
     * 资源表编号
     */
    private String serialNum;

    /**
     * 所属组织id
     */
    private String orgId;

    /**
     * 所属组织
     */
    private String orgName;

    /**
     * 主题id
     */
    private String themeId;

    /**
     * 资源分类id
     */
    private String resourceId;

    public BaseInfo() {
    }

    public BaseInfo(String id, String name, String chineseName, Integer externalState, Integer columnsCount, Long dataSize, String source, String descriptor, String matters, String serialNum, String orgId, String orgName, String themeId, String resourceId) {
        this.id = id;
        this.name = name;
        this.chineseName = chineseName;
        this.externalState = externalState;
        this.columnsCount = columnsCount;
        this.dataSize = dataSize;
        this.source = source;
        this.descriptor = descriptor;
        this.matters = matters;
        this.serialNum = serialNum;
        this.orgId = orgId;
        this.orgName = orgName;
        this.themeId = themeId;
        this.resourceId = resourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getExternalState() {
        return externalState;
    }

    public void setExternalState(Integer externalState) {
        this.externalState = externalState;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getMatters() {
        return matters;
    }

    public void setMatters(String matters) {
        this.matters = matters;
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

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
