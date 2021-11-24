package com.hashtech.web.result;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
public class ThemeResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 上级目录id，没有则为0
     */
    private String parentId ;

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
    private Integer sort;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 删除标识
     */
    private String delFlag = "N";
}
