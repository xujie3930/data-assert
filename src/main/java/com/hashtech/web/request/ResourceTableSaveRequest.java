package com.hashtech.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 添加资源表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableSaveRequest {
    /**
     * 所属资源id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer state = 0;
    /**
     * 表名称
     */
    private String name;
    /**
     * 数据项
     */
    private Integer columnsCount;
    /**
     * 数据量
     */
    private Long dataSize;
    /**
     * 数据来源
     */
    private String source;
    /**
     * 描述
     */
    private String descriptor;
    /**
     * 注意事项
     */
    private String matters;
    /**
     * 排序按照数据库中的排列
     */
    private Integer sort;
}