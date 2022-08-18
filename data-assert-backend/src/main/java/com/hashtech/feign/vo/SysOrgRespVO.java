package com.hashtech.feign.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/** @author roy */
public class SysOrgRespVO implements Serializable {

  private static final long serialVersionUID = 6913634126861139441L;

  private String id;

  private String key;

  private String title;

  private String orgCode;

  private String shortName;

  private Boolean status;

  private Boolean parentStatus;

  private Integer members;

  private Integer orderBy;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  private String contact;

  private String phone;

  private String creditCode;

  private String orgCategory;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return id;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Boolean getParentStatus() {
    return parentStatus;
  }

  public void setParentStatus(Boolean parentStatus) {
    this.parentStatus = parentStatus;
  }

  public Integer getMembers() {
    return members;
  }

  public void setMembers(Integer members) {
    this.members = members;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  public String getOrgCategory() {
    return orgCategory;
  }

  public void setOrgCategory(String orgCategory) {
    this.orgCategory = orgCategory;
  }

  @Override
  public String toString() {
    return "SysOrgRespVO{" +
            "id='" + id + '\'' +
            ", key='" + key + '\'' +
            ", title='" + title + '\'' +
            ", orgCode='" + orgCode + '\'' +
            ", shortName='" + shortName + '\'' +
            ", status=" + status +
            ", parentStatus=" + parentStatus +
            ", members=" + members +
            ", orderBy=" + orderBy +
            ", createTime=" + createTime +
            ", contact='" + contact + '\'' +
            ", phone='" + phone + '\'' +
            ", creditCode='" + creditCode + '\'' +
            ", orgCategory='" + orgCategory + '\'' +
            '}';
  }
}
