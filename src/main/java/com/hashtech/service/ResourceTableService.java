package com.hashtech.service;

import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.ResourceTableInfoResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主题资源表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
public interface ResourceTableService extends IService<ResourceTableEntity> {

    BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request);

    BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request);

    BusinessResult<ResourceTableInfoResult> getResourceTableInfo(ResourceTableInfoRequest request);

    BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids);

    BusinessResult<BusinessPageResult> pageList(ResourceTablePageListRequest request);

    BusinessResult<List<Map<Integer, String>>> getDataSource();

    BusinessResult<List<Object>> getResourceData(ResourceDataRequest request);
}
