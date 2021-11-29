package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.CollectionUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.common.StatusEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.utils.DoubleUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主题资源表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Service
public class ThemeResourceServiceImpl extends ServiceImpl<ThemeResourceMapper, ThemeResourceEntity> implements ThemeResourceService {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ThemeResourceMapper themeResourceMapper;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    private static final String THEME_PARENT_ID = "0";

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveTheme(String userId, ThemeSaveRequest request) {
        checkRepetitionName(request.getName());
        ThemeResourceEntity entity = getThemeResourceEntitySave(userId, request);
        return BusinessResult.success(save(entity));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<ThemeResult> getThemeInfo(String id) {
        ThemeResourceEntity entity = getById(id);
        ThemeResult result = BeanCopyUtils.copyProperties(entity, new ThemeResult());
        //该主题下的资源分类信息
        List<ThemeResourceEntity> resourceList = themeResourceMapper.getResourceByParentId(id);
        result.setResourceList(resourceList);
        return BusinessResult.success(result);
    }

    private void checkRepetitionName(String name) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitName(name));
        if (hasExit) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000002.getCode());
        }
    }

    private ThemeResourceEntity getThemeResourceEntitySave(String userId, ThemeSaveRequest request) {
        ThemeResourceEntity entity = new ThemeResourceEntity();
        BeanCopyUtils.copyProperties(request, entity);
        Date date = new Date();
        entity.setCreateBy(userId);
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setUpdateBy(userId);
        entity.setParentId(THEME_PARENT_ID);
        entity.setId(sequenceService.nextValueString());
        //TODO:查询和插入得保证原子性
        Integer maxSort = themeResourceMapper.getMaxSort();
        entity.setSort(maxSort + 1);
        return entity;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateTheme(String userId, ThemeUpdateRequest request) {
        checkRepetitionName(request.getName());
        ThemeResourceEntity entity = new ThemeResourceEntity();
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(userId);
        entity.setName(request.getName());
        entity.setId(request.getThemeId());
        return BusinessResult.success(updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteTheme(String userId, ThemeDeleteRequest request) {
        ThemeResourceEntity entity = getThemeResourceEntityDel(userId, request);
        return BusinessResult.success(updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveResource(String userId, ResourceSaveRequest request) {
        checkRepetitionNameByResource(request.getName());
        ThemeResourceEntity entity = getResourceEntity(userId, request);
        return BusinessResult.success(save(entity));
    }

    private void checkRepetitionNameByResource(String name) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitNameByResource(name));
        if (hasExit) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000003.getCode());
        }
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<ResourceResult> getResourceInfo(String id) {
        ResourceResult result = new ResourceResult();
        ThemeResourceEntity resourceEntity = getById(id);
        result.setName(resourceEntity.getName());
        result.setDescriptor(resourceEntity.getDescriptor());
        List<ResourceTableEntity> resourceTableList = resourceTableMapper.getListByResourceId(id);
        long openCount = resourceTableList.stream().filter(entity -> StatusEnum.ENABLE.getCode().equals(entity.getState())).count();
        if (resourceTableList.size() == 0){
            result.setOpenRate(1.00);
        }else {
            result.setOpenRate(DoubleUtils.formatDouble(openCount / (double) resourceTableList.size()));
        }
        result.setColumnsCount(resourceTableList.parallelStream().mapToInt(ResourceTableEntity::getColumnsCount).sum());
        result.setTableCount(resourceTableList.size());
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateResource(String userId, ResourceUpdateRequest request) {
        checkRepetitionNameByResource(request.getName());
        ThemeResourceEntity entity = new ThemeResourceEntity();
        BeanCopyUtils.copyProperties(request, entity);
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(userId);
        return BusinessResult.success(updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteResource(String userId, ResourceDeleteRequest request) {
        resourceTableMapper.deleteByResourceId(request.getId());
        ThemeResourceEntity entity = new ThemeResourceEntity();
        entity.setId(request.getId());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        entity.setDelFlag("Y");
        return BusinessResult.success(updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> rearrangement(String userId, Map<String, String[]> request) {
        if (CollectionUtils.isEmpty(request)) {
            return BusinessResult.success(true);
        }
        int size = request.entrySet().size();
        for (Map.Entry<String, String[]> m : request.entrySet()) {

            String themeId = m.getKey();
            String[] resourceIds = m.getValue();
            //调换不同主题之间的排序
            themeResourceMapper.updateSort(size, themeId);
            size -= 1;
            if (resourceIds.length <= 0) {
                continue;
            }
            //更新资源所属主题
            themeResourceMapper.updateParentId(themeId, resourceIds);
        }
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<Boolean> hasExitTheme(ThemeSaveRequest request) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitName(request.getName()));
        return BusinessResult.success(hasExit);
    }

    @Override
    public BusinessResult<Boolean> hasExitResource(ResourceSaveRequest request) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitNameByResource(request.getName()));
        return BusinessResult.success(hasExit);
    }

    @Override
    public BusinessResult<List<ThemeResult>> getList() {
        List<ThemeResult> list = themeResourceMapper.queryPage();
        return BusinessResult.success(list);
    }

    private ThemeResourceEntity getResourceEntity(String userId, ResourceSaveRequest request) {
        ThemeResourceEntity entity = BeanCopyUtils.copyProperties(request, new ThemeResourceEntity());
        Date date = new Date();
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setParentId(request.getId());
        entity.setId(sequenceService.nextValueString());
        //TODO:查询和插入得保证原子性
        Integer maxSort = themeResourceMapper.getMaxSortByParentId(request.getId());
        entity.setSort(maxSort + 1);
        return entity;
    }

    private ThemeResourceEntity getThemeResourceEntityDel(String userId, ThemeDeleteRequest request) {
        ThemeResourceEntity entity = new ThemeResourceEntity();
        entity.setId(request.getId());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        entity.setDelFlag("Y");
        return entity;
    }
}
