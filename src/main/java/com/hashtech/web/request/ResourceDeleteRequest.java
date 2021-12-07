package com.hashtech.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author xujie
 * @description 删除资源分类请求参数
 * @create 2021-11-24 14:01
 **/
@Data
public class ResourceDeleteRequest {
    /**
     * 资源分类id
     */
    private String id;

    public ResourceDeleteRequest() {
    }

    public ResourceDeleteRequest(String id) {
        this.id = id;
    }
}
