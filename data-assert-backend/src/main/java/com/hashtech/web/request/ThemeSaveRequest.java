package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存主题入参
 * @create 2022-01-18 18:50
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 添加主题请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class ThemeSaveRequest {

    @NotBlank(message = "60000004")
    @Length(max = 12, message = "60000000")
    private String name;

    public ThemeSaveRequest() {
    }

    public ThemeSaveRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

