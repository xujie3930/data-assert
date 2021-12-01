package com.hashtech.web.result;

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
 * @since 2021-12-1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableSettingResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表的访问url
     */
    private String requestUrl;

    /**
     * 支持格式：0-JSON
     */
    private Integer formats = 0;

    /**
     * 请求方式：0-POST,1-GET
     */
    private Integer requestWay = 0;

    /**
     * 请求实例说明
     */
    private String explainInfo;

    /**
     * 参数信息
     */
    private List<String> paramInfo;
}
