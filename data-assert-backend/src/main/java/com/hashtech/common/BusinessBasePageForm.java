package com.hashtech.common;

public class BusinessBasePageForm {
    private int pageNum = 1;
    private int pageSize = 10;
    private int pageStart = (pageNum-1)*pageSize;
    private String orderBy;
    private String order;

    public BusinessBasePageForm() {
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum >= 1) {
            this.pageNum = pageNum;
            this.pageStart = (this.pageNum-1)*this.pageSize;
        }

    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize >= 1) {
            this.pageSize = pageSize;
            this.pageStart = (this.pageNum-1)*this.pageSize;
        }

    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }
}
