package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存标签
 * @create 2022-04-07 11:08
 **/

//@Data
public class TagChangeStateRequest {
    //主键id
    private String id;
    //是否启用：0-停用，1-启用
    private Integer state;

    public TagChangeStateRequest() {
    }

    public TagChangeStateRequest(String id, Integer state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

