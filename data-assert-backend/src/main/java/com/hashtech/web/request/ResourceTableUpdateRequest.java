package com.hashtech.web.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class ResourceTableUpdateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 表名
     */
    private String name;
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
     * 资源表编号
     */
    private String serialNum;

    /**
     * 所属组织Id
     */
    private String orgId;

    /**
     * 所属组织Id
     */
    private String orgName;

    /**
     * 表中文名称
     */
    @NotBlank(message = "60000034")
    @Length(max = 50, message = "表名称最多为50字")
    private String chineseName;

    /**
     * 数据源id
     */
    private String datasourceId;

    /**
     * 脱敏字段
     */
    private List<String> desensitizeFields;

    /**
     * 主数据类别
     */
    private Integer masterDataId;

    /**
     * 是否为主数据，0-是，1-否，默认0（2022-03-24新增）
     */
    private Integer masterDataFlag;

    /**
     * 数据源类型：1-mysql,2-oracle,3-postgre
     */
    private Integer type;

    public ResourceTableUpdateRequest() {
    }

    public ResourceTableUpdateRequest(String id, String name, String source, String descriptor, String matters, String serialNum, String orgId, String orgName, String chineseName, String datasourceId, List<String> desensitizeFields, Integer masterDataId, Integer masterDataFlag, Integer type) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.descriptor = descriptor;
        this.matters = matters;
        this.serialNum = serialNum;
        this.orgId = orgId;
        this.orgName = orgName;
        this.chineseName = chineseName;
        this.datasourceId = datasourceId;
        this.desensitizeFields = desensitizeFields;
        this.masterDataId = masterDataId;
        this.masterDataFlag = masterDataFlag;
        this.type = type;
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

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
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

    public Integer getMasterDataId() {
        return masterDataId;
    }

    public void setMasterDataId(Integer masterDataId) {
        this.masterDataId = masterDataId;
    }

    public Integer getMasterDataFlag() {
        return masterDataFlag;
    }

    public void setMasterDataFlag(Integer masterDataFlag) {
        this.masterDataFlag = masterDataFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
