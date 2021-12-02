package com.hashtech.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.businessframework.result.BusinessPageResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.Date;
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
public class ResourceTableInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 基础信息
     */
    public BaseInfo baseInfo;
    /**
     * 表结构
     */
    public List<Structure> structureList;
    /**
     * 采样数据
     */
    public BusinessPageResult<Object> sampleList;
}
