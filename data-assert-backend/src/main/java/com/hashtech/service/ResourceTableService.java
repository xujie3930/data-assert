package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.feign.result.ResourceTableResult;
import com.hashtech.web.request.*;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;

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

    /**
     * 注意：此方法被切面使用，故方法的入参（String userId, ResourceTableUpdateRequest request）必须在前面且顺序不可改变，但可以在后面追加入参参数
     * @author  maoc
     * @create  2022/6/7 11:37
     * @desc
     **/
    BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request);

    BusinessResult<BaseInfo> getResourceTableBaseInfo(ResourceTableBaseInfoRequest request);

    BusinessResult<List<Structure>> getResourceTableStructureList(ResourceTableNameRequest request);

    BusinessResult<BusinessPageResult<Object>> getResourceTableSampleList(ResourceTablePreposeRequest request);

    BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids);

    BusinessResult<BusinessPageResult> pageList(ResourceTablePageListRequest request);

    BusinessResult<List<Map<Integer, String>>> getDataSource();

    Boolean hasExitSerialNum(HasExitSerialNumRequest request);

    Boolean hasExistOpenExternalState(ExistOpenExternalRequest request);

    ResourceTableResult getByResourceTableId(String resourceTableId);

    BusinessResult<List<ResourceTableEntity>> getList(ResourceTableListRequest request);

    BusinessResult<Boolean> updateResourceTableState(String userId, ResourceTableUpdateStateRequest request);

    Map<String, Object> dataPreview(ResourceDataPreviewRequest request);

    void updateThemIdByResourceId(String themeId, String resourceId);

    void updateThemIdByResourceIds(String themeId, String[] resourceIds);

    void updateMasterDataByResourceIds(Integer code, Integer id, String[] resourceIds);
}
