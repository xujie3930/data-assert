package com.hashtech.service;

import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.ResourceTableSaveRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.ResourceTableInfoResult;
import com.hashtech.web.result.ResourceTablePreposeResult;

import java.util.List;

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

    BusinessResult<ResourceTableInfoResult> getResourceTableInfo(String id);

    BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids);

    BusinessPageResult pageList(ResourceTablePageListRequest request);

    BusinessResult<List<String>> getTablaList();

    BusinessResult<ResourceTablePreposeResult> getTablaInfo(ResourceTablePreposeRequest request);
}
