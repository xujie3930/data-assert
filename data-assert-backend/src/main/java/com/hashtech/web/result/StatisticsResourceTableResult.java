package com.hashtech.web.result;

/**
 * @author xujie
 * @description 资源表统计返回
 * @create 2022-04-11 15:56
 **/
public class StatisticsResourceTableResult {

    /**
     * 目录总量(资源表数量)
     */
    private Long directoryCount;

    /**
     * 数据项总量
     */
    private Long columnCount;

    /**
     * 数据总量
     */
    private Long dataCount;

    public StatisticsResourceTableResult() {
    }

    public StatisticsResourceTableResult(Long directoryCount, Long columnCount, Long dataCount) {
        this.directoryCount = directoryCount;
        this.columnCount = columnCount;
        this.dataCount = dataCount;
    }

    public Long getDirectoryCount() {
        return directoryCount;
    }

    public void setDirectoryCount(Long directoryCount) {
        this.directoryCount = directoryCount;
    }

    public Long getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Long columnCount) {
        this.columnCount = columnCount;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }
}
