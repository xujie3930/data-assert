package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.request.ResourceTableSaveRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.ResourceTableInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private ThemeResourceMapper themeResourceMapper;
    @Autowired
    private ResourceTableMapper resourceTableMapper;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        ResourceTableEntity entity = getResourceTableEntitySave(userId, request);
        return BusinessResult.success(save(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        return BusinessResult.success(updateById(entity));
    }

    @Override
    public BusinessResult<ResourceTableInfoResult> getResourceTableInfo(String id) {
        ResourceTableEntity entity = getById(id);
        ResourceTableInfoResult result = BeanCopyUtils.copyProperties(entity, new ResourceTableInfoResult());
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
    public BusinessPageResult pageList(ResourceTablePageListRequest request) {
        Page<ResourceTableEntity> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<ResourceTableEntity> list = resourceTableMapper.queryPage(page, request);
        return BusinessPageResult.build(page.setRecords(list), request);
    }

    private ResourceTableEntity getResourceTableEntitySave(String userId, ResourceTableSaveRequest request) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setId(sequenceService.nextValueString());
        entity.setResourceId(request.getId());
        return entity;
    }
}
