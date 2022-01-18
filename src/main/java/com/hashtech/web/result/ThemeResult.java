package com.hashtech.web.result;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.entity.ThemeResourceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
     * 该主题下的资源列表
     */
    private List<ThemeResult> resourceList = new LinkedList<>();

    /**
     * 是否隐藏编辑按钮，true：隐藏，false：不隐藏
     */
    private Boolean hidden;

    /**
     * 创建者id
     */
    private String createUserId;

    /**
     * icron图标
     */
    private String picPath;

    /**
     * icron图标路径
     */
    private String picUrl;
}
