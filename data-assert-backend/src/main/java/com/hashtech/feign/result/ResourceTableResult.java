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

    private String datasourceId;

    private String desensitizeFields;

    private List<String> params = new ArrayList<>();

    private List<String> resps = new ArrayList<>();

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

    public List<String> getResps() {
        return resps;
    }

    public void setResps(List<String> resps) {
        this.resps = resps;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDesensitizeFields() {
        return desensitizeFields;
    }

    public void setDesensitizeFields(String desensitizeFields) {
        this.desensitizeFields = desensitizeFields;
    }
}
