package com.hashtech.web.result;

import lombok.Data;

/**
 * @author xujie
 * @description
 * @create 2021-11-29 18:57
 **/
@Data
public class BaseInfo {
    /**
     * 主键id
     */
    private String id;
    /**
     * 表名称,自动识别
     */
    private String name;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer state = 0;
    /**
     * 资源分类列表展示的列表的总字段，自动识别
     */
    private Integer columnsCount;
    /**
     * 数据总条数，自动识别
     */
    private Integer dataSize;
    /**
     * 数据来源
     */
    private String source;
    /**
     * 表描述信息
     */
    private String descriptor;
    /**
     * 注意事项
     */
    private String matters;
}
