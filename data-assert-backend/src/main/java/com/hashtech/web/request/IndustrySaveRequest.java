package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存标签
 * @create 2022-04-07 11:08
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

//@Data
public class IndustrySaveRequest {

    @NotBlank(message = "70000023")
    @Length(max = 50, message = "70000024")
    private String name;

    //描述
    @Length(max = 200, message = "70000025")
    private String desc;

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

    public IndustrySaveRequest() {
    }

    public IndustrySaveRequest(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}

