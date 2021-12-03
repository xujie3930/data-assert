package com.hashtech.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableUpdateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer state = 0;
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
}
