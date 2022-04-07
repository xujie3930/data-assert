package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存标签
 * @create 2022-04-07 11:08
 **/

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 添加主题请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class TagUpdateRequest {

    /**
     * 主键id
     */
    private String id;

    @NotBlank(message = "70000006")
    @Length(max = 50, message = "70000001")
    private String name;

    //标签描述
    @Length(max = 50, message = "70000002")
    private String describe;

    //是否启用：0-否，1-是,默认1
    private Integer state;

    public TagUpdateRequest() {
    }

    public TagUpdateRequest(String id, String name, String describe, Integer state) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.state = state;
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
}

