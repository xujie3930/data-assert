package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("company_info")
public class CompanyInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统一社会信用代码
     */
    private String uscc;

    /**
     * 组织机构名称
     */
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
    private String describe;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

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
    private LocalDateTime updateTime;

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
    private Boolean delFlag;


}
