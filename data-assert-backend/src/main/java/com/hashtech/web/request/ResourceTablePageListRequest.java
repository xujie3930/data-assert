package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 资源表列表请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class ResourceTablePageListRequest extends BusinessBasePageForm {
    @NotBlank(message = "60000007")
    /**
     * 所属资源id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer externalState;
    /**
     * 表名称
     */
    private String name;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 排序
     */
    private String ascOrDesc = "desc";
    /**
     * 根据状态分组：开启分组后，0-开放在前，1-不开放在后
     */
    private Boolean stateGroup;

    public ResourceTablePageListRequest() {
    }

    public ResourceTablePageListRequest(String id, Integer externalState, String name, String createBy, String ascOrDesc, Boolean stateGroup) {
        this.id = id;
        this.externalState = externalState;
        this.name = name;
        this.createBy = createBy;
        this.ascOrDesc = ascOrDesc;
        this.stateGroup = stateGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getExternalState() {
        return externalState;
    }

    public void setExternalState(Integer externalState) {
        this.externalState = externalState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public Boolean getStateGroup() {
        return stateGroup;
    }

    public void setStateGroup(Boolean stateGroup) {
        this.stateGroup = stateGroup;
    }
}
