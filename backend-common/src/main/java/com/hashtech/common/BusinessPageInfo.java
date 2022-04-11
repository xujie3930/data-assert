package com.hashtech.common;

public class BusinessPageInfo {
    private int pageNum;
    private int pageSize;
    private String orderBy;
    private String order;

    public BusinessPageInfo(int currentPage, int pageSize) {
        this(currentPage, pageSize, (String) null, (String) null);
    }

    public BusinessPageInfo(int currentPage, int pageSize, String orderBy) {
        this(currentPage, pageSize, orderBy, "DESC");
    }

    public BusinessPageInfo(int currentPage, int pageSize, String orderBy, String order) {
        if (currentPage >= 1 && pageSize >= 1) {
            this.pageNum = currentPage;
            this.pageSize = pageSize;
            this.order = order;
            this.orderBy = orderBy;
        } else {
            throw new IllegalArgumentException("currentPage and pageSize must more than 0.");
        }
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return this.orderBy + " " + this.order;
    }

    public int getStartRow() {
        return (this.pageNum - 1) * this.pageSize;
    }
}
