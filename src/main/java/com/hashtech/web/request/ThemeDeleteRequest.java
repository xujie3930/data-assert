package com.hashtech.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 删除主题请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ThemeDeleteRequest {

    /**
     * 主题id
     */
    private String id;
}
