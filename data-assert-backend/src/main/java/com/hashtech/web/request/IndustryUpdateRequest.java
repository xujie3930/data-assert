package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存标签
 * @create 2022-04-07 11:08
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

//@Data
public class IndustryUpdateRequest {

    //主键id
    @NotBlank(message = "产业id不能为空")
    private String id;

    @NotBlank(message = "70000023")
    @Length(max = 50, message = "70000024")
    private String name;

    //描述
    @Length(max = 200, message = "70000025")
    private String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public IndustryUpdateRequest() {
    }

    public IndustryUpdateRequest(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}

