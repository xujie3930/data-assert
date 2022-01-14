package com.hashtech.web.result;

import lombok.Data;

/**
 * @author xujie
 * @description 资源分类信息
 * @create 2021-11-24 15:04
 **/
@Data
public class ResourceResult {
    /**
     * 资源分类名称
     */
    private String name;
    /**
     * 新增资源分类时填写的信息回显
     */
    private String descriptor;
    /**
     * 该资源分类下所包含的表数量
     */
    private Integer tableCount;
    /**
     * 资源分类列表展示的列表的总字段
     */
    private Integer columnsCount;
    /**
     * 数据总条数
     */
    private Long dataSize;
    /**
     * 该资源分类下所包含的表，处于开放状态的表所占总表数的百分比
     * 初始状态为0
     */
    private Double openRate = 0.00;

    /**
     * 资源分类图标
     */
    private String picPath;
}
