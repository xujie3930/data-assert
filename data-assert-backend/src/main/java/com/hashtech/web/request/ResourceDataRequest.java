package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

import java.util.LinkedHashMap;

/**
 * @author xujie
 * @description 获取资源表数据的url
 * @create 2021-12-06 15:32
 **/
//@Data
public class ResourceDataRequest extends BusinessBasePageForm {
    /**
     * 请求URL，具有唯一性,一个URL对应资源库中的一张表
     */
    private String requestUrl;
    /**
     * 开放平台携带过来的请求参数Map<参数字段名:参数值>
     */
    private LinkedHashMap<String, Object> params;

    public ResourceDataRequest() {
    }

    public ResourceDataRequest(String requestUrl, LinkedHashMap<String, Object> params) {
        this.requestUrl = requestUrl;
        this.params = params;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public LinkedHashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(LinkedHashMap<String, Object> params) {
        this.params = params;
    }
}
