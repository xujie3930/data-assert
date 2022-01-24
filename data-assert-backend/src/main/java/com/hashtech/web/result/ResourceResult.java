package com.hashtech.web.result;

/**
 * @author xujie
 * @description 资源分类信息
 * @create 2021-11-24 15:04
 **/
//@Data
public class ResourceResult {
    /**
     * 资源分类名称
     */
    private String name;
    /**
     * 新增资源分类时填写的信息回显
     */
    private String descriptor;
    /**
     * 该资源分类下所包含的表数量
     */
    private Integer tableCount;
    /**
     * 资源分类列表展示的列表的总字段
     */
    private Integer columnsCount;
    /**
     * 数据总条数
     */
    private Long dataSize;
    /**
     * 该资源分类下所包含的表，处于开放状态的表所占总表数的百分比
     * 初始状态为0
     */
    private Double openRate = 0.00;

    /**
     * 资源分类图标
     */
    private String picPath;

    /**
     * 分类图标路径
     */
    private String picUrl;

    public ResourceResult() {
    }

    public ResourceResult(String name, String descriptor, Integer tableCount, Integer columnsCount, Long dataSize, Double openRate, String picPath, String picUrl) {
        this.name = name;
        this.descriptor = descriptor;
        this.tableCount = tableCount;
        this.columnsCount = columnsCount;
        this.dataSize = dataSize;
        this.openRate = openRate;
        this.picPath = picPath;
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Integer getTableCount() {
        return tableCount;
    }

    public void setTableCount(Integer tableCount) {
        this.tableCount = tableCount;
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(Integer columnsCount) {
        this.columnsCount = columnsCount;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public Double getOpenRate() {
        return openRate;
    }

    public void setOpenRate(Double openRate) {
        this.openRate = openRate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
