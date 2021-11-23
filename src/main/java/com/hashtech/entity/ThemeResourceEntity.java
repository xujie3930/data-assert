package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
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
     * 用于主题和资源分类排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;


}
