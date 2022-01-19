package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

/**
 * @author xujie
 * @description 根据表明获取源库信息
 * @create 2021-11-29 17:40
 **/
//@Data
public class ResourceTablePreposeRequest extends BusinessBasePageForm {
    private String tableName;

    public ResourceTablePreposeRequest() {
    }

    public ResourceTablePreposeRequest(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
