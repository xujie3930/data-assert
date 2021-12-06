package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.ResourceTablePreviewRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.ResourceTablePreposeResult;
import com.hashtech.web.result.TablePreviewResult;
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

    BusinessResult<TablePreviewResult> previewTableSetting(String userId, ResourceTablePreviewRequest request);

    BusinessResult<ResourceTablePreposeResult> getTablaInfo(ResourceTablePreposeRequest request);

    BusinessResult<List<Map<String, String>>> getTablaList();
}
