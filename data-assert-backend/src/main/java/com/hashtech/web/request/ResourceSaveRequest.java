package com.hashtech.web.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 添加资源分类请求参数
 * @create 2021-11-24 14:01
 **/
//@Data
public class ResourceSaveRequest {
    /**
     * 所属主题id
     */
    private String id;
    /**
     * 资源分类名称
     */
    @NotBlank(message = "60000005")
    @Length(max = 20, message = "60000012")
    private String name;
    /**
     * 资源分类描述
     */
    @Length(max = 200, message = "60000001")
    private String descriptor;

    /**
     * 分类图标
     */
    private String picPath;

    /**
     * 分类图标路径
     */
    private String picUrl;

    public ResourceSaveRequest() {
    }

    public ResourceSaveRequest(String id, String name, String descriptor, String picPath, String picUrl) {
        this.id = id;
        this.name = name;
        this.descriptor = descriptor;
        this.picPath = picPath;
        this.picUrl = picUrl;
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
