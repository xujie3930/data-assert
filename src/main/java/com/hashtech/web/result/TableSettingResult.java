package com.hashtech.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
     * 入参信息
     */
    private List<Structure> paramInfo;

    /**
     * 出参信息
     */
    private List<Structure> outParamInfo;

    /**
     * 表结构
     */
    private List<Structure> structureList;

    /**
     * 接口名称
     */
    private String interfaceName;
}
