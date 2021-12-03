package com.hashtech.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class TableSettingUpdateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 请求方式：0-POST,1-GET
     */
    private Integer requestWay = 0;
    /**
     * 请求示例说明
     */
    private String explainInfo;
    /**
     * 参数信息
     */
    private List<String> paramInfo = new LinkedList<>();
}
