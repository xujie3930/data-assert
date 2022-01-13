package com.hashtech.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 资源表列表请求参数(不分页)
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableListRequest {
    @NotBlank(message = "60000007")
    /**
     * 所属资源id
     */
    private String id;
}
