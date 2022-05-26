package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存主题入参
 * @create 2022-01-18 18:50
 **/

/**
 * @author xujie
 * @description 添加主题请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class MasterDataJudgeRequest {

    private String themeId;

    private String resourceId;

    public MasterDataJudgeRequest() {
    }

    public MasterDataJudgeRequest(String themeId, String resourceId) {
        this.themeId = themeId;
        this.resourceId = resourceId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}

