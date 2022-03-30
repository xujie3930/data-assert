package com.hashtech.web.request;

/**
 *
 */
public class DataSourcesListRequest {

    /**
     * 数据源类型：1-mysql，2-oracle，3-postgresql，4-sqlServer，5-MongoDB
     */
    private Integer type;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
