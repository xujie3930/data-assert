package com.hashtech.web.request;

/**
 * @author xujie
 * @description 删除资源分类请求参数
 * @create 2021-11-24 14:01
 **/
//@Data
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
