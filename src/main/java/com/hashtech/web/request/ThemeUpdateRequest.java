package com.hashtech.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 添加主题请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ThemeUpdateRequest {

    /**
     * 主题id
     */
    private String themeId;
    /**
     * 主题名称
     */
    private String name;
}
