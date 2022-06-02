package com.hashtech.web.request;

import com.hashtech.feign.result.AppAuthResult;
import com.hashtech.web.result.TableSettingAppsResult;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
//@Data
public class TableSettingUpdateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 表的访问url
     */
    private String requestUrl;
    /**
     * 请求方式：0-POST,1-GET
     */
    private Integer requestWay = 1;
    /**
     * 请求示例说明
     */
    private String explainInfo;
    /**
     * 参数信息
     */
    private String[] paramInfo;
    /**
     * 返回字段信息
     */
    private String[] respInfo;

    /**
     * 返回APP信息
     */
    private List<TableSettingAppsResult> appList;

    /**
     * 应用授权信息
     */
    private AppAuthResult authResult;

    /**
     * 接口名称
     */
    @NotBlank(message = "60000029")
    @Length(max = 200, message = "接口名称在50子以内")
    private String interfaceName;

    public TableSettingUpdateRequest() {
    }

    public TableSettingUpdateRequest(String id, String requestUrl, Integer requestWay, String explainInfo, String[] paramInfo, String interfaceName) {
        this.id = id;
        this.requestUrl = requestUrl;
        this.requestWay = requestWay;
        this.explainInfo = explainInfo;
        this.paramInfo = paramInfo;
        this.interfaceName = interfaceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getRequestWay() {
        return requestWay;
    }

    public void setRequestWay(Integer requestWay) {
        this.requestWay = requestWay;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }

    public String[] getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(String[] paramInfo) {
        this.paramInfo = paramInfo;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String[] getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(String[] respInfo) {
        this.respInfo = respInfo;
    }

    public List<TableSettingAppsResult> getAppList() {
        return appList;
    }

    public void setAppList(List<TableSettingAppsResult> appList) {
        this.appList = appList;
    }

    public AppAuthResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult(AppAuthResult authResult) {
        this.authResult = authResult;
    }
}
