package com.hashtech.feign.result;

import java.util.List;

public class ApiGroupIdAAndNameResult {

    private String id;
    private String name;
    private List<ApiIdAAndNameResult> children;
    private Boolean leaf;
    private Integer level;
    private String parentId;

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

    public List<ApiIdAAndNameResult> getChildren() {
        return children;
    }

    public void setChildren(List<ApiIdAAndNameResult> children) {
        this.children = children;
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
}
