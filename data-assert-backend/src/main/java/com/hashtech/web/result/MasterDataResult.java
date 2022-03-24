package com.hashtech.web.result;

/**
 * @author xujie
 * @description 主数据返回信息
 * @create 2022-03-24 13:55
 **/
public class MasterDataResult {
    /**
     * 是否为主数据，0-是，1-否，默认0（2022-03-24新增）
     */
    private Integer masterDataFlag;

    /**
     * 主数据类别id
     */
    private Integer masterDataId;

    /**
     * 主数据类别名称
     */
    private String masterDataName;

    public MasterDataResult() {
    }

    public MasterDataResult(Integer masterDataFlag, Integer masterDataId, String masterDataName) {
        this.masterDataFlag = masterDataFlag;
        this.masterDataId = masterDataId;
        this.masterDataName = masterDataName;
    }

    public Integer getMasterDataFlag() {
        return masterDataFlag;
    }

    public void setMasterDataFlag(Integer masterDataFlag) {
        this.masterDataFlag = masterDataFlag;
    }

    public Integer getMasterDataId() {
        return masterDataId;
    }

    public void setMasterDataId(Integer masterDataId) {
        this.masterDataId = masterDataId;
    }

    public String getMasterDataName() {
        return masterDataName;
    }

    public void setMasterDataName(String masterDataName) {
        this.masterDataName = masterDataName;
    }
}
