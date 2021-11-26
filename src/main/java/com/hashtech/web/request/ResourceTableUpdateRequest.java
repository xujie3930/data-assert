package com.hashtech.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableUpdateRequest {
    /**
     * 主键id
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
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
