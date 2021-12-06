package com.hashtech.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author xujie
 * @description 获取资源表数据的url
 * @create 2021-12-06 15:32
 **/
@Data
public class ResourceDataRequest extends BusinessBasePageForm {
    private String requestUrl;
}
