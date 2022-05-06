package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.MasterDataEntity;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.feign.DataApiFeignClient;
import com.hashtech.feign.ServeFeignClient;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.feign.result.ResourceTableResult;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.DataSourceMapper;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.*;
import com.hashtech.service.bo.TableFieldsBO;
import com.hashtech.utils.CharUtil;
import com.hashtech.utils.DBConnectionManager;
import com.hashtech.web.request.*;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 主题资源表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Service
public class ResourceTableServiceImpl extends ServiceImpl<ResourceTableMapper, ResourceTableEntity> implements ResourceTableService {

    private final Logger logger = LoggerFactory.getLogger(ResourceTableServiceImpl.class);
    @Resource
    private ThemeResourceService themeResourceService;
    @Resource
    private ResourceTableMapper resourceTableMapper;
    @Resource
    private TableSettingMapper tableSettingMapper;
    @Resource
    private TableSettingService tableSettingService;
    @Resource
    private DataSourceMapper dataSourceMapper;
    @Resource
    private OauthApiService oauthApiService;
    @Resource
    private RomoteDataSourceService romoteDataSourceService;
    @Resource
    private ServeFeignClient serveFeignClient;
    @Resource
    private MasterDataService masterDataService;
    @Autowired
    private DataApiFeignClient dataApiFeignClient;

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        logger.info("request:{}", request);
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkTableChineseName(request.getChineseName());
        checkTableHasExist(request.getChineseName(), null);
        ThemeResourceEntity resourceEntity = themeResourceService.getById(request.getResourceId());
        if (Objects.isNull(resourceEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000007.getCode());
        }
        if (ThemeResourceServiceImpl.getThemeParentId().equals(resourceEntity.getParentId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000022.getCode());
        }
        checkHasExitResourceTable(request.getName(), request.getDatasourceId(), null);
        checkHasExitSerialNum(new HasExitSerialNumRequest(request.getSerialNum(), null));
        BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(request.getDatasourceId(), request.getName()));
        ResourceTableEntity entity = getResourceTableEntitySave(user, request, baseInfo, resourceEntity);
        save(entity);
        TableSettingEntity tableSettingEntity = getTableSettingSaveEntity(entity, request);
        tableSettingService.save(tableSettingEntity);
        return BusinessResult.success(true);
    }

    private void checkHasExitSerialNum(HasExitSerialNumRequest request) {
        Boolean hasExist = hasExitSerialNum(request);
        if (hasExist) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000027.getCode());
        }
    }

    private Boolean checkHasExitResourceTable(String name, String datasourceId, String id) {
        boolean hasExit = BooleanUtils.isTrue(resourceTableMapper.checkHasExitResourceTableInAll(name, datasourceId, id));
        if (hasExit) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000031.getCode());
        }
        return hasExit;
    }

    private TableSettingEntity getTableSettingSaveEntity(ResourceTableEntity entity, ResourceTableSaveRequest request) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = new TableSettingEntity();
        tableSettingEntity.setResourceTableId(resourceTableId);
        tableSettingEntity.setDesensitizeFields(StringUtils.join(request.getDesensitizeFields(), ","));
        return tableSettingEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkTableChineseName(request.getChineseName());
        checkTableHasExist(request.getChineseName(), request.getId());
        ResourceTableEntity resourceTableEntity = getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        checkHasExitResourceTable(request.getName(), request.getDatasourceId(), resourceTableEntity.getId());
        checkHasExitSerialNum(new HasExitSerialNumRequest(request.getSerialNum(), request.getId()));
        //不更换表，只更新表信息
        if (resourceTableEntity.getName().equals(request.getName())) {
            ResourceTableEntity entity = getById(request.getId());
            updateMasterData(request, entity);
            BusinessResult<String[]> serveResult = serveFeignClient.getOpenDirIds(entity.getResourceId());
            if (!serveResult.isSuccess()) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000038.getCode());
            }
            Set<String> openResourceIds = new HashSet<>(Arrays.asList(serveResult.getData()));
            if (openResourceIds.contains(entity.getId())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000040.getCode());
            }
            BeanCopyUtils.copyProperties(request, entity);
            entity.setUpdateBy(user.getUsername());
            entity.setUpdateTime(new Date());
            entity.setChineseName(request.getChineseName());
            updateById(entity);
            TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(entity.getId());
            tableSettingEntity.setDesensitizeFields(StringUtils.join(request.getDesensitizeFields(), ","));
            tableSettingService.updateById(tableSettingEntity);
            return BusinessResult.success(true);
        }
        //更换表，则同时更新更新表信息和表设置
        BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(request.getDatasourceId(), request.getName()));
        ResourceTableEntity entityUpdate = getResourceTableEntityUpdate(userId, request, baseInfo);
        updateById(entityUpdate);
        TableSettingEntity tableSettingUpdateEntity = getTableSettingUpdateEntity(entityUpdate, request);
        return BusinessResult.success(tableSettingService.updateById(tableSettingUpdateEntity));
    }

    private void updateMasterData(ResourceTableUpdateRequest request, ResourceTableEntity entity) {
        if (MasterFlagEnum.YES.getCode().equals(request.getMasterDataFlag())) {
            entity.setResourceId(request.getResourceId());
            ThemeResourceEntity themeResourceEntity = themeResourceService.getById(request.getResourceId());
            entity.setThemeId(Optional.ofNullable(themeResourceEntity).orElse(new ThemeResourceEntity()).getParentId());
        }
    }

    private void checkTableHasExist(String chineseName, String id) {
        if (BooleanUtils.isTrue(resourceTableMapper.checkTableHasExist(chineseName, id))) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000033.getCode());
        }
    }

    private TableSettingEntity getTableSettingUpdateEntity(ResourceTableEntity entity, ResourceTableUpdateRequest request) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableId);
        //更新表后，请求参数、返回参数、脱敏字段置空
        tableSettingEntity.setParamInfo("");
        tableSettingEntity.setRespInfo("");
        tableSettingEntity.setDesensitizeFields(StringUtils.join(request.getDesensitizeFields(), ","));
        return tableSettingEntity;
    }

    @Override
    public BusinessResult<BaseInfo> getResourceTableBaseInfo(ResourceTableBaseInfoRequest request) {
        ResourceTablePreposeRequest preposeRequest = BeanCopyUtils.copyProperties(request, new ResourceTablePreposeRequest());
        //接口详情
        if (!StringUtils.isBlank(request.getId())) {
            ResourceTableEntity entity = getById(request.getId());
            if (Objects.isNull(entity)) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
            }
            //对接开放平台，如果删除了的话，那个data返回为空
            if (DelFalgEnum.HAS_DELETE.getDesc().equals(entity.getDelFlag())) {
                return BusinessResult.success(null);
            }
            preposeRequest.setDatasourceId(entity.getDatasourceId());
            preposeRequest.setTableName(entity.getName());
        } else {
            //添加外部表的前置接口
            preposeRequest.setDatasourceId(request.getDatasourceId());
            preposeRequest.setTableName(request.getTableName());
        }
        BaseInfo baseInfo = tableSettingService.getBaseInfo(preposeRequest);
        //若是详情接口：1，更新表数据量2，返回详情信息
        if (!StringUtils.isBlank(request.getId())) {
            ResourceTableEntity oldEntity = getById(request.getId());
            oldEntity.setDataSize(baseInfo.getDataSize());
            oldEntity.setColumnsCount(baseInfo.getColumnsCount());
            updateById(oldEntity);
            BeanCopyUtils.copyProperties(oldEntity, baseInfo);
            DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
            baseInfo.setType(datasource.getType());
            baseInfo.setDatabaseName(datasource.getName());
            if (StatusEnum.DISABLE.getCode().equals(datasource.getStatus()) || DelFalgEnum.HAS_DELETE.getDesc().equals(datasource.getDelFlag())) {
                baseInfo.setDatasourceId(null);
            }
            setMasterData(baseInfo, oldEntity);
        }
        return BusinessResult.success(baseInfo);
    }

    private void setMasterData(BaseInfo baseInfo, ResourceTableEntity oldEntity) {
        baseInfo.setMasterDataFlag(MasterFlagEnum.NO.getCode());
        baseInfo.setMasterDataId(null);
        baseInfo.setMasterDataName(null);
        MasterDataEntity masterDataEntity = masterDataService.getById(oldEntity.getMasterDataId());
        if (Objects.nonNull(masterDataEntity)) {
            if (MasterFlagEnum.YES.getCode().equals(oldEntity.getMasterDataFlag())) {
                baseInfo.setMasterDataFlag(MasterFlagEnum.YES.getCode());
                baseInfo.setMasterDataId(masterDataEntity.getId());
                baseInfo.setMasterDataName(masterDataEntity.getName());
                ThemeResourceEntity themeResourceEntity = themeResourceService.getById(oldEntity.getResourceId());
                baseInfo.setResourceName(Optional.ofNullable(themeResourceEntity).orElse(new ThemeResourceEntity()).getName());
            }
        }
    }

    @Override
    public BusinessResult<List<Structure>> getResourceTableStructureList(ResourceTableNameRequest request) {
        List<Structure> structureList = tableSettingService.getStructureList(request);
        //若该资源表，存储了脱敏字段
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
        request.setType(datasource.getType());
        ResourceTableEntity resourceTableEntity = resourceTableMapper.getByDatasourceIdAndName(request);
        if (!Objects.isNull(resourceTableEntity)) {
            TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableEntity.getId());
            if (!Objects.isNull(tableSettingEntity) && StringUtils.isNotBlank(tableSettingEntity.getDesensitizeFields())) {
                String[] arr = tableSettingEntity.getDesensitizeFields().split(",");
                Set<String> set = new HashSet<>(Arrays.asList(arr));
                for (Structure structure : structureList) {
                    if (set.contains(structure.getFieldEnglishName())) {
                        structure.setDesensitize(StateEnum.YES.ordinal());
                    }
                }
            }
        }
        return BusinessResult.success(structureList);
    }

    @Override
    public BusinessResult<BusinessPageResult<Object>> getResourceTableSampleList(ResourceTablePreposeRequest request) {
        BusinessPageResult<Object> sampleList = tableSettingService.getSampleList(request);
        return BusinessResult.success(sampleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids) {
        if (ids.length <= 0) {
            return BusinessResult.success(true);
        }
        Set<String> resourceIds = new HashSet<>(Arrays.asList(ids));
        String resourceId = getById(ids[0]).getResourceId();
        BusinessResult<String[]> result = serveFeignClient.getOpenDirIds(resourceId);
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000038.getCode());
        }
        Set<String> openResourceIds = new HashSet<>(Arrays.asList(result.getData()));
        openResourceIds.retainAll(resourceIds);
        if (openResourceIds.size() > 0) {
            if (ids.length == 1) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000017.getCode());
            }
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000018.getCode());
        }
        //变更资源表状态
        List<String> apiPathList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (String id : ids) {
            ResourceTableEntity entity = getById(id);
            apiPathList.add(entity.getRequestUrl());
            idList.add(id);
        }
        //变更资源表表设置状态
        tableSettingService.updateTableSettingState(ids, DelFalgEnum.HAS_DELETE.getDesc());
        //批量删除数据服务对应API信息
        dataApiFeignClient.deleteByPath(userId, apiPathList);
        removeByIds(idList);
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult> pageList(ResourceTablePageListRequest request) {
        QueryWrapper<ResourceTableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(ResourceTableEntity.DEL_FLAG, DelFalgEnum.NOT_DELETE.getDesc());
        wrapper.eq(ResourceTableEntity.RESOURCE_ID, request.getId());
        if (!StringUtils.isBlank(request.getName())) {
            wrapper.like(ResourceTableEntity.CHINESE_NAME, request.getName());
        }
        if (!StringUtils.isBlank(request.getCreateBy())) {
            wrapper.like(ResourceTableEntity.CREATE_BY, request.getCreateBy());
        }
        if (SortEnum.DESC.getDesc().equals(request.getAscOrDesc())) {
            wrapper.orderByDesc(ResourceTableEntity.CREATE_TIME);
        }
        if (SortEnum.ASC.getDesc().equals(request.getAscOrDesc())) {
            wrapper.orderByAsc(ResourceTableEntity.CREATE_TIME);
        }
        IPage<ResourceTableEntity> page = this.page(
                new Query<ResourceTableEntity>().getPage(request),
                wrapper
        );
        return BusinessResult.success(BusinessPageResult.build(page, request));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<ResourceTableEntity>> getList(ResourceTableListRequest request) {
        QueryWrapper<ResourceTableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(ResourceTableEntity.DEL_FLAG, DelFalgEnum.NOT_DELETE.getDesc());
        wrapper.eq(ResourceTableEntity.RESOURCE_ID, request.getId());
        List<ResourceTableEntity> list = list(wrapper);
        return BusinessResult.success(list);
    }

    @Override
    public BusinessResult<Boolean> updateResourceTableState(String userId, ResourceTableUpdateStateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        ResourceTableEntity entity = getById(request.getId());
        if (Objects.isNull(entity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        entity.setUpdateBy(user.getUsername());
        entity.setUpdateTime(new Date());
        return BusinessResult.success(updateById(entity));
    }

    @Override

    public BusinessResult<List<Map<Integer, String>>> getDataSource() {
        return BusinessResult.success(dataSourceMapper.getList());
    }

    @Override
    public Boolean hasExitSerialNum(HasExitSerialNumRequest request) {
        return BooleanUtils.isTrue(resourceTableMapper.hasExitSerialNum(request.getSerialNum(), request.getId()));
    }

    @Override
    public Boolean hasExistOpenExternalState(ExistOpenExternalRequest request) {
//        String[] resourceIds = {request.getResourceId()};
//        if (!StringUtils.isBlank(request.getThemeId())) {
//            List<ThemeResult> resourceList = themeResourceMapper.getResourceByParentId(request.getThemeId());
//            List<String> collect = resourceList.stream().map(ThemeResult::getId).collect(Collectors.toList());
//            resourceIds = collect.toArray(new String[collect.size()]);
//        }
//        return BooleanUtils.isTrue(resourceTableMapper.hasExitExternalStateByResourceIds(resourceIds, StatusEnum.ENABLE.getCode()));
        BusinessResult<Map<String, List<String>>> result = serveFeignClient.getOpenTopicAndClassifyIds();
        if (!result.isSuccess() || CollectionUtils.isEmpty(result.getData())) {
            return true;
        }
        Map<String, List<String>> map = result.getData();
        //所有在开放平台开放的主题
        List<String> openTopicIds = map.get("topicIds");
        //所有在开放平台开放的资源分类
        List<String> openResourceIds = map.get("classifyIds");
        if (openTopicIds.contains(request.getThemeId()) || openResourceIds.contains(request.getResourceId())) {
            return false;
        }
        return true;
    }

    @Override
    public ResourceTableResult getByResourceTableId(String resourceTableId) {
        ResourceTableResult result = new ResourceTableResult();
        ResourceTableEntity resourceTable = resourceTableMapper.getByResourceTableId(resourceTableId);
        if (Objects.isNull(resourceTable)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        result.setTableName(resourceTable.getName());
        result.setDatasourceId(resourceTable.getDatasourceId());
        TableSettingEntity tableSetting = tableSettingMapper.getByResourceTableId(resourceTable.getId());
        result.setDesensitizeFields(tableSetting.getDesensitizeFields());
        if (Objects.isNull(resourceTable)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        if (StringUtils.isNotBlank(tableSetting.getParamInfo())) {
            String[] split = tableSetting.getParamInfo().split(",");
            List<String> paramList = Stream.of(split).collect(Collectors.toList());
            result.setParams(paramList);
        }
        if (StringUtils.isNotBlank(tableSetting.getRespInfo())) {
            String[] respInfoArr = tableSetting.getRespInfo().split(",");
            List<String> respInfoList = Stream.of(respInfoArr).collect(Collectors.toList());
            result.setResps(respInfoList);
        }
        return result;
    }

    private ResourceTableEntity getResourceTableEntitySave(InternalUserInfoVO user, ResourceTableSaveRequest request, BaseInfo baseInfo, ThemeResourceEntity resourceEntity) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        Date date = new Date();
        entity.setId(null);
        entity.setResourceId(request.getResourceId());
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setCreateBy(user.getUsername());
        entity.setCreateUserId(user.getUserId());
        entity.setUpdateBy(user.getUsername());
        entity.setResourceId(request.getResourceId());
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        //保存主题id
        entity.setThemeId(resourceEntity.getParentId());
        entity.setDatasourceId(request.getDatasourceId());
        return entity;
    }

    private void checkTableChineseName(String chineseName) {
        if (CharUtil.isSpecialChar(chineseName)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000032.getCode());
        }
    }

    private ResourceTableEntity getResourceTableEntityUpdate(String userId, ResourceTableUpdateRequest request, BaseInfo baseInfo) {
        Date date = new Date();
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateTime(date);
        entity.setUpdateBy(userId);
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        entity.setDatasourceId(request.getDatasourceId());
        updateMasterData(request, entity);
        return entity;
    }

    @Override
    public Map<String, Object> dataPreview(ResourceDataPreviewRequest request) {
        ResourceTableEntity resourceTable = resourceTableMapper.selectById(request.getResourceTableId());
        ResourceTablePreposeRequest resourceRequest = new ResourceTablePreposeRequest(resourceTable.getDatasourceId(), resourceTable.getName());
        TableFieldsBO tableFieldsBO = tableSettingService.getTableColumnChineseName(resourceTable.getName(), resourceTable.getDatasourceId());
        String fields = tableFieldsBO.getFields();

        BusinessPageResult<Object> pageResult = tableSettingService.getResourceDataList(resourceRequest, fields);

        List<List<String>> list = new LinkedList<>();
        for (Object o : pageResult.getList()) {
            List<String> l = castList(o, String.class);
            list.add(l);
        }

        Map<String, Object> result = new HashMap<>();
        List<List<String>> headList = new LinkedList<>();
        headList.add(tableFieldsBO.getFieldChineseNameList());
        result.put("headList", headList);
        result.put("resultList", BusinessPageResult.build(list, request, 10));
        return result;
    }

    @Override
    public void updateThemIdByResourceId(String themeId, String resourceId) {
        resourceTableMapper.updateThemIdByResourceId(themeId, resourceId);
    }

    @Override
    public void updateThemIdByResourceIds(String themeId, String[] resourceIds) {
        resourceTableMapper.updateThemIdByResourceIds(themeId, resourceIds);
    }

    //将object转换为list
    private <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> resultList = new LinkedList<>();
        if (obj instanceof Map<?, ?>) {
            for (Object o : ((Map) obj).keySet()) {
                resultList.add(clazz.cast(((Map) obj).get(o)));
            }
            return resultList;
        }
        return null;
    }
}