package com.hashtech.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 更新资源表状态请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableUpdateStateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 是否开放：0-开放，1-不开放
     */
    private Integer externalState;
}
