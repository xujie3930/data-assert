package com.hashtech.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author xujie
 * @description 更新资源分类请求参数
 * @create 2021-11-24 14:01
 **/
@Data
public class ResourceUpdateRequest {
    /**
     * 资源分类id
     */
    private String id;
    /**
     * 资源分类名称
     */
    @Length(max = 50, message = "60000000")
    private String name;
    /**
     * 资源分类描述
     */
    @Length(max = 200, message = "60000001")
    private String descriptor;

}
