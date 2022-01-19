package com.hashtech.web.result;

import com.hashtech.common.BusinessPageResult;

import java.util.List;

/**
 * @author xujie
 * @description 资源表前置返回值
 * @create 2021-11-29 17:58
 **/
//@Data
public class ResourceTablePreposeResult {

    //基础信息
    public BaseInfo baseInfo;
    //表结构
    public List<Structure> structureList;
    //采样数据
    public BusinessPageResult<Object> sampleList;

    public ResourceTablePreposeResult() {
    }

    public ResourceTablePreposeResult(BaseInfo baseInfo, List<Structure> structureList, BusinessPageResult<Object> sampleList) {
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
