package com.hashtech.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 用于判断主题、资源能否编辑（存在在开放平台开放的表时候，不支持编辑）
 * @create 2022-01-05 19:06
 **/
@Data
public class ExistOpenExternalRequest {
    /**
     * 主题id
     */
    private String themeId;
    /**
     * 资源id
     */
    private String resourceId;

    public ExistOpenExternalRequest() {
    }

    public ExistOpenExternalRequest(String themeId, String resourceId) {
        this.themeId = themeId;
        this.resourceId = resourceId;
    }
}
