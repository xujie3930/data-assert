package com.hashtech.feign.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description
 * @create 2022-01-20 09:53
 **/
public class ResourceTableResult {

    private String tableName;

    private String uri;

    private List<String> params = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public ResourceTableResult() {
    }

    public ResourceTableResult(String tableName, List<String> params) {
        this.tableName = tableName;
        this.params = params;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
