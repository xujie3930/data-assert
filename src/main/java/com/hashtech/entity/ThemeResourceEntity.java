package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.common.DelFalgEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("theme_resource")
public class ThemeResourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 上级目录id，没有则为0
     */
    private String parentId;

    /**
     * 主题或者资源分类名称
     */
    private String name;

    /**
     * 主题或者资源分类描述
     */
    private String descriptor;

    /**
     * 用于目录树排序
     */
    private Integer sort = 0;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
     * 更新人
     */
    private String updateBy;

    /**
     * 删除标识
     */
    private String delFlag = DelFalgEnum.NOT_DELETE.getDesc();
}
