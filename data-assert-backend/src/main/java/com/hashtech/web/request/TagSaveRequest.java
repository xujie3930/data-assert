package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存标签
 * @create 2022-04-07 11:08
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@Data
public class TagSaveRequest {

    private Long categoryId = 1l;//默认分组

    @NotBlank(message = "70000005")
    @Length(max = 8, message = "70000000")
    private String code;

    @NotBlank(message = "70000006")
    @Length(max = 50, message = "70000002")
    private String name;

    //标签描述
    @Length(max = 200, message = "70000001")
    private String describe;

    //是否启用：0-否，1-是,默认1
    private Integer state;

    public TagSaveRequest() {
    }

    public TagSaveRequest(String code, String name, String describe, Integer state) {
        this.code = code;
        this.name = name;
        this.describe = describe;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

