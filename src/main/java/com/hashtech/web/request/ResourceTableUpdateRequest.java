package com.hashtech.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 编辑资源表请求参数
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableUpdateRequest {
    @NotBlank(message = "60000008")
    /**
     * 主键id
     */
    private String id;
    /**
     * 表名
     */
    private String name;
    /**
     * 数据来源
     */
    private String source;
    /**
     * 描述
     */
    @Length(max = 200, message = "描述不能多于200字")
    private String descriptor;
    /**
     * 注意事项
     */
    @Length(max = 200, message = "注意事项不能多于200字")
    private String matters;

    /**
     * 资源表编号
     */
    private String serialNum;

    /**
     * 所属组织Id
     */
    private String orgId;

    /**
     * 所属组织Id
     */
    private String orgName;

    /**
     * 表中文名称
     */
    @NotBlank(message = "60000034")
    @Length(max = 50, message = "表名称最多为50字")
    private String chineseName;
}
