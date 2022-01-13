package com.hashtech.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.StringUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.DelFalgEnum;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.common.SortEnum;
import com.hashtech.common.StatusEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.mapper.DataSourceMapper;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.utils.URLProcessUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.ThemeResult;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ThemeResourceService themeResourceService;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private TableSettingService tableSettingService;
    @Autowired
    private DataSourceMapper dataSourceMapper;
    @Autowired
    private ThemeResourceMapper themeResourceMapper;

    @Override
    @DS("master")
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        ThemeResourceEntity resourceEntity = themeResourceService.getById(request.getId());
        if (Objects.isNull(resourceEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000007.getCode());
        }
        if (ThemeResourceServiceImpl.getThemeParentId().equals(resourceEntity.getParentId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000022.getCode());
        }
        checkHasExitResourceTable(request.getName(), null);
        checkHasExitSerialNum(new HasExitSerialNumRequest(request.getSerialNum(), null));
        BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(request.getName()));
        ResourceTableEntity entity = getResourceTableEntitySave(userId, request, baseInfo, resourceEntity);
        save(entity);
        TableSettingEntity tableSettingEntity = getTableSettingSaveEntity(entity);
        tableSettingService.save(tableSettingEntity);
        return BusinessResult.success(true);
    }

    private void checkHasExitSerialNum(HasExitSerialNumRequest request) {
        Boolean hasExist = hasExitSerialNum(request);
        if (hasExist){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000027.getCode());
        }
    }

    private Boolean checkHasExitResourceTable(String name, String id) {
        boolean hasExit = BooleanUtils.isTrue(resourceTableMapper.checkHasExitResourceTableInAll(name, id));
        if (hasExit) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000031.getCode());
        }
        return hasExit;
    }

    private TableSettingEntity getTableSettingSaveEntity(ResourceTableEntity entity) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = new TableSettingEntity();
        tableSettingEntity.setId(sequenceService.nextValueString());
        tableSettingEntity.setResourceTableId(resourceTableId);
        return tableSettingEntity;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request) {
        ResourceTableEntity resourceTableEntity = getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        checkHasExitResourceTable(request.getName(),resourceTableEntity.getId());
        checkHasExitSerialNum(new HasExitSerialNumRequest(request.getSerialNum(), request.getId()));
        //不更换表，只更新表信息
        if (resourceTableEntity.getName().equals(request.getName())) {
            ResourceTableEntity entity = getById(request.getId());
            BeanCopyUtils.copyProperties(request, entity);
            entity.setUpdateBy(userId);
            entity.setUpdateTime(new Date());
            return BusinessResult.success(updateById(entity));
        }
        //更换表，则同时更新更新表信息和表设置
        BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(request.getName()));
        ResourceTableEntity entityUpdate = getResourceTableEntityUpdate(userId, request, baseInfo);
        updateById(entityUpdate);
        TableSettingEntity tableSettingUpdateEntity = getTableSettingUpdateEntity(entityUpdate);
        return BusinessResult.success(tableSettingService.updateById(tableSettingUpdateEntity));
    }

    private TableSettingEntity getTableSettingUpdateEntity(ResourceTableEntity entity) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableId);
        tableSettingEntity.setParamInfo(null);
        return tableSettingEntity;
    }

    @Override
    @DS("master")
    @Deprecated
    public BusinessResult<BaseInfo> getResourceTableBaseInfo(ResourceTableBaseInfoRequest request) {
        ResourceTablePreposeRequest preposeRequest = BeanCopyUtils.copyProperties(request, new ResourceTablePreposeRequest());
        //接口详情
        if (!StringUtils.isBlank(request.getId())) {
            ResourceTableEntity entity = getById(request.getId());
            preposeRequest.setTableName(entity.getName());
        } else {
            //添加外部表的前置接口
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
        }
        return BusinessResult.success(baseInfo);
    }

    @Override
    @DS("master")
    public BusinessResult<List<Structure>> getResourceTableStructureList(ResourceTableNameRequest request) {
        List<Structure> structureList = tableSettingService.getStructureList(request.getTableName());
        return BusinessResult.success(structureList);
    }

    @Override
    @DS("master")
    public BusinessResult<BusinessPageResult<Object>> getResourceTableSampleList(ResourceTablePreposeRequest request) {
        BusinessPageResult<Object> sampleList = tableSettingService.getSampleList(request);
        return BusinessResult.success(sampleList);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids) {
        if (ids.length <= 0) {
            return BusinessResult.success(true);
        }
        //开放平台中开放的表不能删除
        if (BooleanUtils.isTrue(resourceTableMapper.hasExitExternalStateByIds(ids, StatusEnum.ENABLE.getCode()))) {
            if (ids.length == 1) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000017.getCode());
            }
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000018.getCode());
        }
        List<ResourceTableEntity> list = new ArrayList<>();
        for (String id : ids) {
            ResourceTableEntity entity = new ResourceTableEntity();
            entity.setId(id);
            entity.setUpdateTime(new Date());
            entity.setUpdateBy(userId);
            entity.setDelFlag(DelFalgEnum.HAS_DELETE.getDesc());
            list.add(entity);
        }
        return BusinessResult.success(saveOrUpdateBatch(list));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult> pageList(ResourceTablePageListRequest request) {
        QueryWrapper<ResourceTableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(ResourceTableEntity.DEL_FLAG, DelFalgEnum.NOT_DELETE.getDesc());
        wrapper.eq(ResourceTableEntity.RESOURCE_ID, request.getId());
        if (null != request.getExternalState()) {
            wrapper.eq(ResourceTableEntity.EXTERNAL_STATE, request.getExternalState());
        }
        if (!StringUtils.isBlank(request.getName())) {
            wrapper.like(ResourceTableEntity.NAME, request.getName());
        }
        if (!StringUtils.isBlank(request.getCreateBy())) {
            wrapper.like(ResourceTableEntity.CREATE_BY, request.getCreateBy());
        }
        if (BooleanUtils.isTrue(request.getStateGroup())) {
            wrapper.orderByAsc(ResourceTableEntity.EXTERNAL_STATE);
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
    public BusinessResult<List<Map<Integer, String>>> getDataSource() {
        return BusinessResult.success(dataSourceMapper.getList());
    }

    @Override
    public BusinessResult<List<Object>> getResourceData(ResourceDataRequest request) {
        String requestUrl = URLProcessUtils.getRequestUrl(request.getRequestUrl());
        ResourceTableEntity resourceTableEntity = getByRequestUrl(requestUrl);
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000011.getCode());
        }
        List<Object> list = tableSettingService.getResourceData(request, resourceTableEntity);
        return BusinessResult.success(list);
    }

    @Override
    public Boolean hasExitSerialNum(HasExitSerialNumRequest request) {
        return BooleanUtils.isTrue(resourceTableMapper.hasExitSerialNum(request.getSerialNum(), request.getId()));
    }

    @Override
    public Boolean hasExistOpenExternalState(ExistOpenExternalRequest request) {
        String[] resourceIds = {request.getResourceId()};
        if (!StringUtils.isBlank(request.getThemeId())){
            List<ThemeResult> resourceList = themeResourceMapper.getResourceByParentId(request.getThemeId());
            List<String> collect = resourceList.stream().map(ThemeResult::getId).collect(Collectors.toList());
            resourceIds = collect.toArray(new String[collect.size()]);
        }
        return BooleanUtils.isTrue(resourceTableMapper.hasExitExternalStateByResourceIds(resourceIds, StatusEnum.ENABLE.getCode()));
    }

    @Override
    public ResourceTableEntity getByRequestUrl(String requestUrl) {
        return resourceTableMapper.getByRequestUrl(requestUrl);
    }

    private ResourceTableEntity getResourceTableEntitySave(String userId, ResourceTableSaveRequest request, BaseInfo baseInfo, ThemeResourceEntity resourceEntity) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setId(sequenceService.nextValueString());
        entity.setResourceId(request.getId());
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        //保存主题id
        entity.setThemeId(resourceEntity.getParentId());
        return entity;
    }

    private ResourceTableEntity getResourceTableEntityUpdate(String userId, ResourceTableUpdateRequest request, BaseInfo baseInfo) {
        Date date = new Date();
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateTime(date);
        entity.setUpdateBy(userId);
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        return entity;
    }
}
