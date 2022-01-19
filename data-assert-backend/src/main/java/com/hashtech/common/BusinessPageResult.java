package com.hashtech.common;


import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

public class BusinessPageResult<T> implements Serializable {
    private static final long serialVersionUID = -3628865867907230918L;
    private long total;
    private long pageCount;
    private long pageNum;
    private long pageSize;
    private List<T> list;

    private BusinessPageResult() {
    }

    /**
     * @deprecated
     */
    @Deprecated
    public BusinessPageResult(List<T> list, BusinessPageInfo pageQueryRequest, long total) {
        BusinessPageResult result = build(list, pageQueryRequest, total);
        this.total = result.getTotal();
        this.pageCount = result.getPageCount();
        this.pageNum = result.getPageNum();
        this.pageSize = result.getPageSize();
        this.list = list;
    }

    public BusinessPageResult(List<T> list, BusinessBasePageForm pageQueryRequest, long total) {
        BusinessPageResult result = build(list, pageQueryRequest, total);
        this.total = result.getTotal();
        this.pageCount = result.getPageCount();
        this.pageNum = result.getPageNum();
        this.pageSize = result.getPageSize();
        this.list = list;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> BusinessPageResult<T> build(List<T> list, BusinessPageInfo pageQueryRequest, long total) {
        int pageSize = pageQueryRequest.getPageSize();
        int pageNum = pageQueryRequest.getPageNum();
        if (total >= 0L && pageSize > 0 && pageNum >= 0) {
            BusinessPageResult<T> result = new BusinessPageResult();
            result.setList(list);
            result.setTotal(total);
            result.setPageSize((long) pageSize);
            result.setPageNum((long) pageNum);
            if (total == 0L) {
                result.setPageCount(0L);
            } else if (total % (long) pageSize > 0L) {
                result.setPageCount(total / (long) pageSize + 1L);
            } else {
                result.setPageCount(total / (long) pageSize);
            }

            return result;
        } else {
            throw new IllegalArgumentException("total must more than 0");
        }
    }

    public static <T> BusinessPageResult<T> build(List<T> list, BusinessBasePageForm pageQueryRequest, long total) {
        int pageSize = pageQueryRequest.getPageSize();
        int pageNum = pageQueryRequest.getPageNum();
        if (total >= 0L && pageSize > 0 && pageNum >= 0) {
            BusinessPageResult<T> result = new BusinessPageResult();
            result.setList(list);
            result.setTotal(total);
            result.setPageSize((long) pageSize);
            result.setPageNum((long) pageNum);
            if (total == 0L) {
                result.setPageCount(0L);
            } else if (total % (long) pageSize > 0L) {
                result.setPageCount(total / (long) pageSize + 1L);
            } else {
                result.setPageCount(total / (long) pageSize);
            }

            return result;
        } else {
            throw new IllegalArgumentException("total must more than 0");
        }
    }

    public static <T> BusinessPageResult<T> build(IPage<T> page, BusinessBasePageForm pageQueryRequest) {
        int pageSize = pageQueryRequest.getPageSize();
        int pageNum = pageQueryRequest.getPageNum();
        long total = page.getTotal();
        if (total >= 0L && pageSize > 0 && pageNum >= 0) {
            BusinessPageResult<T> result = new BusinessPageResult();
            result.setList(page.getRecords());
            result.setTotal(total);
            result.setPageSize((long) pageSize);
            result.setPageNum((long) pageNum);
            if (total == 0L) {
                result.setPageCount(0L);
            } else if (total % (long) pageSize > 0L) {
                result.setPageCount(total / (long) pageSize + 1L);
            } else {
                result.setPageCount(total / (long) pageSize);
            }

            return result;
        } else {
            throw new IllegalArgumentException("total must more than 0");
        }
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}