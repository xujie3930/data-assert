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
public class ResourceTableInfoRequest extends BusinessBasePageForm {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
}
