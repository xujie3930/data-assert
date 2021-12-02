package com.hashtech.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xujie
 * @description 预览接口参数
 * @create 2021-12-02 14:32
 **/
@Data
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
}
