package com.hashtech.web.result;

import lombok.Data;

/**
 * @author xujie
 * @description 返回当前删除主题、资源的id，及其下一个id，无下一个id就返回第一个id
 * 前端用做高亮
 * @create 2021-12-16 16:25
 **/
@Data
public class IdResult {
    private String current;
    private String next;
    private String previous;
    //当前资源的主题id
    private String themeId;
}
