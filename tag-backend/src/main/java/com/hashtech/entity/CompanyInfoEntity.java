package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("company_info")
public class CompanyInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String DEL_FLAG = "del_flag";
    public static final String USCC = "uscc";
    public static final String CORP_NM = "corp_nm";
    public static final String ID = "id";
    public static final String TAG_NUM = "tag_num";
    public static final String UPDATE_TIME = "update_time";

    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 统一社会信用代码
     */
    @TableField("`uscc`")
    private String uscc;

    /**
     * 组织机构名称
     */
    @TableField("`corp_nm`")
    private String corpNm;

    /**
     * 法人类型
     */
    private String shrhlrType;

    /**
     * 注册地址
     */
    private String rgstAddr;

    /**
     * 注册地址邮政编码
     */
    private String rgstAddrPstcd;

    /**
     * 注册地行政区划代码
     */
    private String rgstAddrAdmnDidNbr;

    /**
     * 经营范围
     */
    private String operateRng;

    /**
     * 注册资金
     */
    private String rgstAmo;

    /**
     * 法定代表人姓名
     */
    private String shrhlrNm;

    /**
     * 法定代表人身份证件类型
     */
    private String shrhlrCerType;

    /**
     * 法定代表人身份证件号码
     */
    private String shrhlrCerNbr;

    /**
     * 法人联系电话
     */
    private String shrhlrPh;

    /**
     * 成立日期
     */
    private String establishDt;

    /**
     * 登记业务类型
     */
    private String rgstBsnType;

    /**
     * 核准日期
     */
    private String approvalDt;

    /**
     * 最后更新日期
     */
    private String lastChgDt;

    /**
     * 状态
     */
    private String status;

    /**
     * 生产经营地址
     */
    private String chrnOperateRng;

    /**
     * 生产经营地址行政区划
     */
    private String chrnOperateRngAdmnDid;

    /**
     * 货币种类
     */
    private String fndMntrType;

    /**
     * 注册号
     */
    private String rgstNbr;

    /**
     * 行业分类代码
     */
    private String idyTypeCode;

    /**
     * 经济类型代码
     */
    private String ecnTypeCode;

    /**
     * 登记管理部门名称
     */
    private String enrlAdminDepNm;

    /**
     * 登记管理部门代码
     */
    private String enrlAdminDepNbr;

    /**
     * 主管单位
     */
    private String spvsrUnit;

    /**
     * 主管单位代码
     */
    private String spvsrUnitCode;

    /**
     * 工商企业类型代码
     */
    private String businessCirclesCorpTpyeNbr;

    /**
     * 有效期自
     */
    private String strtDt;

    /**
     * 有效期至
     */
    private String endDt;

    /**
     * 是否公开
     */
    private String isnoOpen;

    /**
     * 企业描述,200字内可以为空
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人id
     */
    private String createUserId;

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
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 该企业的标签数量
     */
    private Integer tagNum;

    /**
     * 是否删除:0-否，1-删除
     */
    @TableLogic
    private Integer delFlag;

    public CompanyInfoEntity() {
    }

    public CompanyInfoEntity(String id, String uscc, String corpNm, String shrhlrType, String rgstAddr, String rgstAddrPstcd, String rgstAddrAdmnDidNbr, String operateRng, String rgstAmo, String shrhlrNm, String shrhlrCerType, String shrhlrCerNbr, String shrhlrPh, String establishDt, String rgstBsnType, String approvalDt, String lastChgDt, String status, String chrnOperateRng, String chrnOperateRngAdmnDid, String fndMntrType, String rgstNbr, String idyTypeCode, String ecnTypeCode, String enrlAdminDepNm, String enrlAdminDepNbr, String spvsrUnit, String spvsrUnitCode, String businessCirclesCorpTpyeNbr, String strtDt, String endDt, String isnoOpen, String describe, Date createTime, String createUserId, String createBy, Date updateTime, String updateUserId, String updateBy, Integer tagNum, Integer delFlag) {
        this.id = id;
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.shrhlrType = shrhlrType;
        this.rgstAddr = rgstAddr;
        this.rgstAddrPstcd = rgstAddrPstcd;
        this.rgstAddrAdmnDidNbr = rgstAddrAdmnDidNbr;
        this.operateRng = operateRng;
        this.rgstAmo = rgstAmo;
        this.shrhlrNm = shrhlrNm;
        this.shrhlrCerType = shrhlrCerType;
        this.shrhlrCerNbr = shrhlrCerNbr;
        this.shrhlrPh = shrhlrPh;
        this.establishDt = establishDt;
        this.rgstBsnType = rgstBsnType;
        this.approvalDt = approvalDt;
        this.lastChgDt = lastChgDt;
        this.status = status;
        this.chrnOperateRng = chrnOperateRng;
        this.chrnOperateRngAdmnDid = chrnOperateRngAdmnDid;
        this.fndMntrType = fndMntrType;
        this.rgstNbr = rgstNbr;
        this.idyTypeCode = idyTypeCode;
        this.ecnTypeCode = ecnTypeCode;
        this.enrlAdminDepNm = enrlAdminDepNm;
        this.enrlAdminDepNbr = enrlAdminDepNbr;
        this.spvsrUnit = spvsrUnit;
        this.spvsrUnitCode = spvsrUnitCode;
        this.businessCirclesCorpTpyeNbr = businessCirclesCorpTpyeNbr;
        this.strtDt = strtDt;
        this.endDt = endDt;
        this.isnoOpen = isnoOpen;
        this.describe = describe;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.updateBy = updateBy;
        this.tagNum = tagNum;
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getCorpNm() {
        return corpNm;
    }

    public void setCorpNm(String corpNm) {
        this.corpNm = corpNm;
    }

    public String getShrhlrType() {
        return shrhlrType;
    }

    public void setShrhlrType(String shrhlrType) {
        this.shrhlrType = shrhlrType;
    }

    public String getRgstAddr() {
        return rgstAddr;
    }

    public void setRgstAddr(String rgstAddr) {
        this.rgstAddr = rgstAddr;
    }

    public String getRgstAddrPstcd() {
        return rgstAddrPstcd;
    }

    public void setRgstAddrPstcd(String rgstAddrPstcd) {
        this.rgstAddrPstcd = rgstAddrPstcd;
    }

    public String getRgstAddrAdmnDidNbr() {
        return rgstAddrAdmnDidNbr;
    }

    public void setRgstAddrAdmnDidNbr(String rgstAddrAdmnDidNbr) {
        this.rgstAddrAdmnDidNbr = rgstAddrAdmnDidNbr;
    }

    public String getOperateRng() {
        return operateRng;
    }

    public void setOperateRng(String operateRng) {
        this.operateRng = operateRng;
    }

    public String getRgstAmo() {
        return rgstAmo;
    }

    public void setRgstAmo(String rgstAmo) {
        this.rgstAmo = rgstAmo;
    }

    public String getShrhlrNm() {
        return shrhlrNm;
    }

    public void setShrhlrNm(String shrhlrNm) {
        this.shrhlrNm = shrhlrNm;
    }

    public String getShrhlrCerType() {
        return shrhlrCerType;
    }

    public void setShrhlrCerType(String shrhlrCerType) {
        this.shrhlrCerType = shrhlrCerType;
    }

    public String getShrhlrCerNbr() {
        return shrhlrCerNbr;
    }

    public void setShrhlrCerNbr(String shrhlrCerNbr) {
        this.shrhlrCerNbr = shrhlrCerNbr;
    }

    public String getShrhlrPh() {
        return shrhlrPh;
    }

    public void setShrhlrPh(String shrhlrPh) {
        this.shrhlrPh = shrhlrPh;
    }

    public String getEstablishDt() {
        return establishDt;
    }

    public void setEstablishDt(String establishDt) {
        this.establishDt = establishDt;
    }

    public String getRgstBsnType() {
        return rgstBsnType;
    }

    public void setRgstBsnType(String rgstBsnType) {
        this.rgstBsnType = rgstBsnType;
    }

    public String getApprovalDt() {
        return approvalDt;
    }

    public void setApprovalDt(String approvalDt) {
        this.approvalDt = approvalDt;
    }

    public String getLastChgDt() {
        return lastChgDt;
    }

    public void setLastChgDt(String lastChgDt) {
        this.lastChgDt = lastChgDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChrnOperateRng() {
        return chrnOperateRng;
    }

    public void setChrnOperateRng(String chrnOperateRng) {
        this.chrnOperateRng = chrnOperateRng;
    }

    public String getChrnOperateRngAdmnDid() {
        return chrnOperateRngAdmnDid;
    }

    public void setChrnOperateRngAdmnDid(String chrnOperateRngAdmnDid) {
        this.chrnOperateRngAdmnDid = chrnOperateRngAdmnDid;
    }

    public String getFndMntrType() {
        return fndMntrType;
    }

    public void setFndMntrType(String fndMntrType) {
        this.fndMntrType = fndMntrType;
    }

    public String getRgstNbr() {
        return rgstNbr;
    }

    public void setRgstNbr(String rgstNbr) {
        this.rgstNbr = rgstNbr;
    }

    public String getIdyTypeCode() {
        return idyTypeCode;
    }

    public void setIdyTypeCode(String idyTypeCode) {
        this.idyTypeCode = idyTypeCode;
    }

    public String getEcnTypeCode() {
        return ecnTypeCode;
    }

    public void setEcnTypeCode(String ecnTypeCode) {
        this.ecnTypeCode = ecnTypeCode;
    }

    public String getEnrlAdminDepNm() {
        return enrlAdminDepNm;
    }

    public void setEnrlAdminDepNm(String enrlAdminDepNm) {
        this.enrlAdminDepNm = enrlAdminDepNm;
    }

    public String getEnrlAdminDepNbr() {
        return enrlAdminDepNbr;
    }

    public void setEnrlAdminDepNbr(String enrlAdminDepNbr) {
        this.enrlAdminDepNbr = enrlAdminDepNbr;
    }

    public String getSpvsrUnit() {
        return spvsrUnit;
    }

    public void setSpvsrUnit(String spvsrUnit) {
        this.spvsrUnit = spvsrUnit;
    }

    public String getSpvsrUnitCode() {
        return spvsrUnitCode;
    }

    public void setSpvsrUnitCode(String spvsrUnitCode) {
        this.spvsrUnitCode = spvsrUnitCode;
    }

    public String getBusinessCirclesCorpTpyeNbr() {
        return businessCirclesCorpTpyeNbr;
    }

    public void setBusinessCirclesCorpTpyeNbr(String businessCirclesCorpTpyeNbr) {
        this.businessCirclesCorpTpyeNbr = businessCirclesCorpTpyeNbr;
    }

    public String getStrtDt() {
        return strtDt;
    }

    public void setStrtDt(String strtDt) {
        this.strtDt = strtDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getIsnoOpen() {
        return isnoOpen;
    }

    public void setIsnoOpen(String isnoOpen) {
        this.isnoOpen = isnoOpen;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
