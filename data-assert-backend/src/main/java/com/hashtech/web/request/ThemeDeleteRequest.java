package com.hashtech.web.request;

/**
 * @author xujie
 * @description 删除主题请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class ThemeDeleteRequest {

    /**
     * 主题id
     */
    private String id;

    public ThemeDeleteRequest() {
    }

    public ThemeDeleteRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
