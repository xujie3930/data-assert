package com.hashtech.feign.vo;

import java.io.Serializable;

/**
 * @author roy
 * @ApiModel(value = "系统用户分页时的信息 Response VO", description = "相比个人用户基本信息来说，会多角色、所属组织")
 */
public class InternalUserInfoVO extends SysUserBaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String realName;

    private String key;

    private String orgId;

    private String title;

    private Integer orgType;

    private Boolean orgStatus;

    private String roleId;

    private String role;

    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(Boolean orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InternalUserInfoVO{" +
                "userId='" + userId + '\'' +
                ", realName='" + realName + '\'' +
                ", key='" + key + '\'' +
                ", orgId='" + orgId + '\'' +
                ", title='" + title + '\'' +
                ", orgType=" + orgType +
                ", orgStatus=" + orgStatus +
                ", roleId='" + roleId + '\'' +
                ", role='" + role + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
