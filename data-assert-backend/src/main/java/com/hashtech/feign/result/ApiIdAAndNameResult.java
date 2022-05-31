package com.hashtech.feign.result;

public class ApiIdAAndNameResult {

    private String id;
    private String name;
    private Boolean leaf;
    private Integer level;
    private String parentId;
    private String grandParentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGrandParentId() {
        return grandParentId;
    }

    public void setGrandParentId(String grandParentId) {
        this.grandParentId = grandParentId;
    }
}
