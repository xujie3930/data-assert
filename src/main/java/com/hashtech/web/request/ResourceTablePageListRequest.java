package com.hashtech.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author xujie
 * @description 资源表列表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTablePageListRequest extends BusinessBasePageForm {
    @NotBlank(message = "60000007")
    /**
     * 所属资源id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer externalState;
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
    private String ascOrDesc = "desc";
    /**
     * 根据状态分组：开启分组后，0-开放在前，1-不开放在后
     */
    private Boolean stateGroup;
}
