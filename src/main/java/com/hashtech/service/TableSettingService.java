package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.web.request.ExistInterfaceNamelRequest;
import com.hashtech.web.request.ResourceDataRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.TableSettingResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源信息设置表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
public interface TableSettingService extends IService<TableSettingEntity> {

    BusinessResult<TableSettingResult> getTableSetting(String id);

    BusinessResult<Boolean> updateTableSetting(String userId, TableSettingUpdateRequest request);

    BaseInfo getBaseInfo(ResourceTablePreposeRequest request) throws AppException;

    List<Structure> getStructureList(String tableName) throws AppException;

    BusinessPageResult<Object> getSampleList(ResourceTablePreposeRequest request) throws AppException;

    BusinessResult<List<Map<String, String>>> getTablaList();

    List<Object> getResourceData(ResourceDataRequest requestUrl, ResourceTableEntity entity);

    Boolean hasExistInterfaceName(ExistInterfaceNamelRequest request);
}
