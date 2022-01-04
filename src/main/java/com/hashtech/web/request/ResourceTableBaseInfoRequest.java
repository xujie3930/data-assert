package com.hashtech.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author xujie
 * @description id不为空，为详情接口
 * tableName不为空，为添加表的前置接口
 * @create 2021-11-24 10:01
 **/
@Data
public class ResourceTableBaseInfoRequest{
    /**
     * 资源表id
     */
    private String id;

    /**
     * 表名,添加接口时候用表名
     */
    private String tableName;
}
