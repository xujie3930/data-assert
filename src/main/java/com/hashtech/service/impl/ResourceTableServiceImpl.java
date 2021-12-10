package com.hashtech.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.common.SortEnum;
import com.hashtech.common.StatusEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.DataSourceMapper;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.URLProcessUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.*;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Date;

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
    private ThemeResourceMapper themeResourceMapper;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private TableSettingService tableSettingService;
    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    @DS("master")
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        BusinessResult<ResourceTablePreposeResult> result = tableSettingService.getTablaInfo(new ResourceTablePreposeRequest(request.getName()));
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        ResourceTableEntity entity = getResourceTableEntitySave(userId, request, result.getData());
        save(entity);
        TableSettingEntity tableSettingEntity = getTableSettingSaveEntity(result, entity);
        tableSettingService.save(tableSettingEntity);
        return BusinessResult.success(true);
    }

    private TableSettingEntity getTableSettingSaveEntity(BusinessResult<ResourceTablePreposeResult> result, ResourceTableEntity entity) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = new TableSettingEntity();
        tableSettingEntity.setId(sequenceService.nextValueString());
        tableSettingEntity.setResourceTableId(resourceTableId);
        List<Structure> structureList = result.getData().getStructureList();
        tableSettingEntity.setColumnsInfo(JSON.toJSON(structureList).toString());
        //TODO:表结构信息未存储于hdfs中
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
        //不更换表，只更新表信息
        if (resourceTableEntity.getName().equals(request.getName())) {
            ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
            entity.setUpdateBy(userId);
            entity.setUpdateTime(new Date());
            return BusinessResult.success(updateById(entity));
        }
        //更换表，则同时更新更新表信息和表设置
        BusinessResult<ResourceTablePreposeResult> result = tableSettingService.getTablaInfo(new ResourceTablePreposeRequest(request.getName()));
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        ResourceTableEntity entityUpdate = getResourceTableEntityUpdate(userId, request, result.getData());
        updateById(entityUpdate);
        TableSettingEntity tableSettingUpdateEntity = getTableSettingUpdateEntity(result.getData(), entityUpdate);
        return BusinessResult.success(tableSettingService.updateById(tableSettingUpdateEntity));
    }

    private TableSettingEntity getTableSettingUpdateEntity(ResourceTablePreposeResult result, ResourceTableEntity entity) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableId);
        tableSettingEntity.setParamInfo(null);
        tableSettingEntity.setColumnsInfo(JSON.toJSON(result.getStructureList()).toString());
        return tableSettingEntity;
    }

    @Override
    @DS("master")
    /**
     * TODO:这里添加外部表的前置接口和接口详情接口为一个接口，可能会有问题
     */
    public BusinessResult<ResourceTableInfoResult> getResourceTableInfo(ResourceTableInfoRequest request) {
        ResourceTableInfoResult result = new ResourceTableInfoResult();
        BaseInfo baseInfo = null;
        ResourceTablePreposeRequest preposeRequest = BeanCopyUtils.copyProperties(request, new ResourceTablePreposeRequest());
        //接口详情
        if (!StringUtils.isBlank(request.getId())) {
            ResourceTableEntity entity = getById(request.getId());
            baseInfo = BeanCopyUtils.copyProperties(entity, new BaseInfo());
            preposeRequest.setTableName(entity.getName());
        } else {
            //添加外部表的前置接口
            preposeRequest.setTableName(request.getTableName());
        }
        BusinessResult<ResourceTablePreposeResult> tablaInfo = tableSettingService.getTablaInfo(preposeRequest);
        if (tablaInfo.isSuccess()) {
            result.setBaseInfo(tablaInfo.getData().getBaseInfo());
            result.setStructureList(tablaInfo.getData().getStructureList());
            result.setSampleList(tablaInfo.getData().getSampleList());
        }
        //若是详情接口：1，更新表数据量2，返回详情信息
        if (!StringUtils.isBlank(request.getId())) {
            ResourceTableEntity oldEntity = getById(request.getId());
            oldEntity.setDataSize(tablaInfo.getData().getBaseInfo().getDataSize());
            updateById(oldEntity);
            result.setBaseInfo(baseInfo);
        }
        return BusinessResult.success(result);
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
            entity.setDelFlag("Y");
            list.add(entity);
        }
        return BusinessResult.success(saveOrUpdateBatch(list));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult> pageList(ResourceTablePageListRequest request) {
        QueryWrapper<ResourceTableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(ResourceTableEntity.DEL_FLAG, "N");
        wrapper.eq(ResourceTableEntity.RESOURCE_ID, request.getId());
        if (null != request.getState()) {
            wrapper.eq(ResourceTableEntity.STATE, request.getState());
        }
        if (!StringUtils.isBlank(request.getName())) {
            wrapper.like(ResourceTableEntity.NAME, request.getName());
        }
        if (!StringUtils.isBlank(request.getCreateBy())) {
            wrapper.like(ResourceTableEntity.CREATE_BY, request.getCreateBy());
        }
        if (BooleanUtils.isTrue(request.getStateGroup())) {
            wrapper.orderByAsc(ResourceTableEntity.STATE);
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
        if (StatusEnum.DISABLE.getCode().equals(resourceTableEntity.getState())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000021.getCode());
        }
        List<Object> list = tableSettingService.getResourceData(request, resourceTableEntity);
        return BusinessResult.success(list);
    }

    private ResourceTableEntity getByRequestUrl(String requestUrl) {
        return resourceTableMapper.getByRequestUrl(requestUrl);
    }

    private ResourceTableEntity getResourceTableEntitySave(String userId, ResourceTableSaveRequest request, ResourceTablePreposeResult result) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        Date date = new Date();
        BaseInfo baseInfo = result.getBaseInfo();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setId(sequenceService.nextValueString());
        entity.setResourceId(request.getId());
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        return entity;
    }

    private ResourceTableEntity getResourceTableEntityUpdate(String userId, ResourceTableUpdateRequest request, ResourceTablePreposeResult result) {
        Date date = new Date();
        BaseInfo baseInfo = result.getBaseInfo();
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateTime(date);
        entity.setUpdateBy(userId);
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        return entity;
    }
}
