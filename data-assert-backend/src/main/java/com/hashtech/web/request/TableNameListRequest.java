package com.hashtech.web.request;

/**
 * @author xujie
 * @description 根据数据源id查询其下所有表
 * @create 2022-02-14 14:32
 **/
public class TableNameListRequest {
    private String id;

    public TableNameListRequest() {
    }

    public TableNameListRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
