package com.hashtech.web.result;

import com.hashtech.common.BusinessPageResult;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

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
public class ResourceTableInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;

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
    public BusinessPageResult<Object> sampleList;

    public ResourceTableInfoResult() {
    }

    public ResourceTableInfoResult(BaseInfo baseInfo, List<Structure> structureList, BusinessPageResult<Object> sampleList) {
        this.baseInfo = baseInfo;
        this.structureList = structureList;
        this.sampleList = sampleList;
    }

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    public BusinessPageResult<Object> getSampleList() {
        return sampleList;
    }

    public void setSampleList(BusinessPageResult<Object> sampleList) {
        this.sampleList = sampleList;
    }
}
