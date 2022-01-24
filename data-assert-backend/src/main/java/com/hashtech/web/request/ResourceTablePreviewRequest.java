package com.hashtech.web.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xujie
 * @description 预览接口参数
 * @create 2021-12-02 14:32
 **/
//@Data
public class ResourceTablePreviewRequest {
    /**
     * 主键id
     */
    @NotBlank(message = "60000008")
    private String id;

    /**
     * 参数信息
     */
    private List<String> paramInfo;

    public ResourceTablePreviewRequest() {
    }

    public ResourceTablePreviewRequest(String id, List<String> paramInfo) {
        this.id = id;
        this.paramInfo = paramInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(List<String> paramInfo) {
        this.paramInfo = paramInfo;
    }
}
