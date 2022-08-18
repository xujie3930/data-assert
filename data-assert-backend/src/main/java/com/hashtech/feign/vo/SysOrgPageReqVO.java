package com.hashtech.feign.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/** @author roy */
public class SysOrgPageReqVO extends PageParam implements Serializable {

  private static final long serialVersionUID = -2576440414208411809L;

  private String parentId;

  private String name;

  private String code;

  @JsonProperty("status")
  private Boolean status;

  private boolean sort;

  private String orderBy;

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public boolean isSort() {
    return sort;
  }

  public void setSort(boolean sort) {
    this.sort = sort;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public String toString() {
    return "SysOrgPageReqVO{" +
            "parentId='" + parentId + '\'' +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", status=" + status +
            ", sort=" + sort +
            ", orderBy='" + orderBy + '\'' +
            '}';
  }
}
