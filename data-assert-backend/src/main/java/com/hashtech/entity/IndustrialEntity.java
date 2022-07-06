package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产业库
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("industrial")
public class IndustrialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 产业库名称
     */
    private String name;

    /**
     * 上级目录id
     */
    private String parentId;

    /**
     * 描述
     */
    private String desc;

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
     * 是否删除：0-否，1-删除
     */
    private Integer delFlag;


}
