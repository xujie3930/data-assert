package com.hashtech.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

/**
 * @author xujie
 * @description 资源表列表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTablePageListRequest extends BusinessBasePageForm {
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer state = 0;
    /**
     * 表名称
     */
    private String name;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 排序
     */
    private String ascOrDesc;
}
