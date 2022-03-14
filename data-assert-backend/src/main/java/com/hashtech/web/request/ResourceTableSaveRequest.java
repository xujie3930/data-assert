package com.hashtech.web.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 添加资源表请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class ResourceTableSaveRequest {
    /**
     * 所属资源id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer state = 0;
    /**
     * 表名称
     */
    private String name;
    /**
     * 表的访问url
     */
    private String requestUrl;
    /**
     * 数据项
     */
    private Integer columnsCount;
    /**
     * 数据量
     */
    private Long dataSize;
    /**
     * 数据来源
     */
    private String source;
    /**
     * 描述
     */
    @Length(max = 200, message = "描述不能多于200字")
    private String descriptor;
    /**
     * 注意事项
     */
    @Length(max = 200, message = "注意事项不能多于200字")
    private String matters;
    /**
     * 排序按照数据库中的排列
     */
    private Integer sort;

    /**
     * 资源表编号（验证不为空，唯一性）
     */
    @NotBlank(message = "60000025")
    @Length(max = 50, message = "资源编号不能多于50字")
    private String serialNum;

    /**
     * 表中文名称
     */
    private String chineseName;

    /**
     * 所属组织Id
     */
    private String orgId;

    /**
     * 所属组织Id
     */
    private String orgName;

    /**
     * 数据源id
     */
    private String datasourceId;

    /**
     * 脱敏字段
     */
    private List<String> desensitizeFields;

    public ResourceTableSaveRequest() {
    }

    public ResourceTableSaveRequest(String id, Integer state, String name, String requestUrl, Integer columnsCount, Long dataSize, String source, String descriptor, String matters, Integer sort, String serialNum, String chineseName, String orgId, String orgName, String datasourceId, List<String> desensitizeFields) {
        this.id = id;
        this.state = state;
        this.name = name;
        this.requestUrl = requestUrl;
        this.columnsCount = columnsCount;
        this.dataSize = dataSize;
        this.source = source;
        this.descriptor = descriptor;
        this.matters = matters;
        this.sort = sort;
        this.serialNum = serialNum;
        this.chineseName = chineseName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.datasourceId = datasourceId;
        this.desensitizeFields = desensitizeFields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
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

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public List<String> getDesensitizeFields() {
        return desensitizeFields;
    }

    public void setDesensitizeFields(List<String> desensitizeFields) {
        this.desensitizeFields = desensitizeFields;
    }

    @Override
    public String toString() {
        return "ResourceTableSaveRequest{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", columnsCount=" + columnsCount +
                ", dataSize=" + dataSize +
                ", source='" + source + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", matters='" + matters + '\'' +
                ", sort=" + sort +
                ", serialNum='" + serialNum + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", datasourceId='" + datasourceId + '\'' +
                ", desensitizeFields=" + desensitizeFields +
                '}';
    }
}
