package com.hashtech.web.result;

/**
 * @author xujie
 * @description 资源表统计返回
 * @create 2022-04-11 15:56
 **/
public class StatisticsResourceTableResult {

    /**
     * 主题总量
     */
    private Long themeCount;

    /**
     * 资源总量
     */
    private Long resourceCount;

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

    /**
     *
     */
    private Long masterDataCount;

    /**
     * 当日新增资源表数量
     */
    private Long newlyTableCount;

    /**
     * 当日新增资源表数据量
     */
    private Long newlyDataSizeCount;

    public StatisticsResourceTableResult() {
    }

    public StatisticsResourceTableResult(Long themeCount, Long resourceCount, Long directoryCount, Long columnCount, Long dataCount, Long masterDataCount, Long newlyTableCount, Long newlyDataSizeCount) {
        this.themeCount = themeCount;
        this.resourceCount = resourceCount;
        this.directoryCount = directoryCount;
        this.columnCount = columnCount;
        this.dataCount = dataCount;
        this.masterDataCount = masterDataCount;
        this.newlyTableCount = newlyTableCount;
        this.newlyDataSizeCount = newlyDataSizeCount;
    }

    public Long getThemeCount() {
        return themeCount;
    }

    public void setThemeCount(Long themeCount) {
        this.themeCount = themeCount;
    }

    public Long getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Long resourceCount) {
        this.resourceCount = resourceCount;
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

    public Long getMasterDataCount() {
        return masterDataCount;
    }

    public void setMasterDataCount(Long masterDataCount) {
        this.masterDataCount = masterDataCount;
    }

    public Long getNewlyTableCount() {
        return newlyTableCount;
    }

    public void setNewlyTableCount(Long newlyTableCount) {
        this.newlyTableCount = newlyTableCount;
    }

    public Long getNewlyDataSizeCount() {
        return newlyDataSizeCount;
    }

    public void setNewlyDataSizeCount(Long newlyDataSizeCount) {
        this.newlyDataSizeCount = newlyDataSizeCount;
    }
}
