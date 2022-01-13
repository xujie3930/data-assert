package com.hashtech.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 添加资源分类请求参数
 * @create 2021-11-24 14:01
 **/
@Data
public class ResourceSaveRequest {
    /**
     * 所属主题id
     */
    private String id;
    /**
     * 资源分类名称
     */
    @NotBlank(message = "60000005")
    @Length(max = 20, message = "60000012")
    private String name;
    /**
     * 资源分类描述
     */
    @Length(max = 200, message = "60000001")
    private String descriptor;

    /**
     * 分类图标
     */
    private String picPath;

}
