package com.hashtech.web.request;

import org.hibernate.validator.constraints.Length;

/**
 * @author xujie
 * @description 更新资源分类请求参数
 * @create 2021-11-24 14:01
 **/
//@Data
public class ResourceUpdateRequest {
    /**
     * 资源分类id
     */
    private String id;
    /**
     * 资源分类名称
     */
    @Length(max = 20, message = "60000012")
    private String name;
    /**
     * 资源分类描述
     */
    @Length(max = 200, message = "60000001")
    private String descriptor;

    /**
     * 资源分类全称
     */
    @Length(max = 100, message = "60000048")
    private String fullName;

    /**
     * 资源图标
     */
    private String picPath;

    /**
     * 资源图标路径
     */
    private String picUrl;

    public ResourceUpdateRequest() {
    }

    public ResourceUpdateRequest(String id, String name, String descriptor, String picPath, String picUrl, String fullName) {
        this.id = id;
        this.name = name;
        this.descriptor = descriptor;
        this.picPath = picPath;
        this.picUrl = picUrl;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
