package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tag")
public class TagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签代码:8位随机数，不为空且唯一
     */
    private String code;

    /**
     * 标签名称,50字内具有唯一性不为空
     */
    private String name;

    /**
     * 标签描述,200字内可以为空
     */
    private String describe;

    /**
     * 是否启用：0-否，1-是
     */
    private Integer state;

    /**
     * 使用次数
     */
    private Integer usedTime;

    /**
     * 最近使用时间
     */
    private LocalDateTime lastUsedTime;

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
