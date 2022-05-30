package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.AppException;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.feign.result.AppGroupListResult;
import com.hashtech.service.bo.TableFieldsBO;
import com.hashtech.web.request.*;
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

    List<Structure> getStructureList(ResourceTableNameRequest request) throws AppException;

    BusinessPageResult<Object> getSampleList(ResourceTablePreposeRequest request) throws AppException;

    BusinessPageResult<Object> getResourceDataList(ResourceTablePreposeRequest request, String fields) throws AppException;

    TableFieldsBO getTableColumnChineseName(String tableName, String datasourceId);

    BusinessResult<List<Map<String, String>>> getTablaList(TableNameListRequest request);

    Boolean hasExistInterfaceName(ExistInterfaceNamelRequest request);

    void updateTableSettingState(String[] ids, String delFlag);

    List<AppGroupListResult> getAppGroups();
}
