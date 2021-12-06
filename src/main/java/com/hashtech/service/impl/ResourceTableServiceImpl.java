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
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.DataSourceMapper;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.RandomUtils;
import com.hashtech.web.request.ResourceTableInfoRequest;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.ResourceTableSaveRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.ResourceTableInfoResult;
import com.hashtech.web.result.ResourceTablePreposeResult;
import com.hashtech.web.result.Structure;
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
    private TableSettingService tableSettingService;
    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    @DS("master")
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        BusinessResult<ResourceTablePreposeResult> result = tableSettingService.getTablaInfo(new ResourceTablePreposeRequest(request.getName()));
        if (result == null || result.getData() == null) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
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
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    //TODO:支持修改表
    public BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request) {
        ResourceTableEntity resourceTableEntity = getById(request.getId());
        if (Objects.isNull(resourceTableEntity)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        return BusinessResult.success(updateById(entity));
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
        if (!StringUtils.isBlank(request.getId())){
            ResourceTableEntity entity = getById(request.getId());
            baseInfo = BeanCopyUtils.copyProperties(entity, new BaseInfo());
            preposeRequest.setTableName(entity.getName());
        }else {
            preposeRequest.setTableName(request.getTableName());
        }
        BusinessResult<ResourceTablePreposeResult> tablaInfo = tableSettingService.getTablaInfo(preposeRequest);
        if (tablaInfo.isSuccess() && tablaInfo.getData() != null) {
            result.setBaseInfo(tablaInfo.getData().getBaseInfo());
            result.setStructureList(tablaInfo.getData().getStructureList());
            result.setSampleList(tablaInfo.getData().getSampleList());
        }
        //若是详情接口：1，更新表数据量2，返回详情信息
        if (!StringUtils.isBlank(request.getId())){
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
            wrapper.orderByDesc(ResourceTableEntity.UPDATE_TIME);
        }
        if (SortEnum.ASC.getDesc().equals(request.getAscOrDesc())) {
            wrapper.orderByAsc(ResourceTableEntity.UPDATE_TIME);
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
        entity.setRequestUrl(RandomUtils.getRandomExcludeNumber(16));
        return entity;
    }
}
