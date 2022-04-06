package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 企业标签关联表
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("company_tag")
public class CompanyTagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 企业id
     */
    private String companyInfoId;

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
     * 是否删除:0-否，1-删除
     */
    private Boolean delFlag;


}
