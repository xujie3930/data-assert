package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.CollectionUtils;
import com.hashtech.businessframework.utils.StringUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.DelFalgEnum;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.common.StatusEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.feign.SysUserFeignClient;
import com.hashtech.feign.result.CommonResult;
import com.hashtech.feign.result.InternalUserInfoVO;
import com.hashtech.feign.result.SysUserInfoResult;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.utils.DoubleUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.IdResult;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private SysUserFeignClient sysUserFeignClient;

    private static final String THEME_PARENT_ID = "0";

    public static String getThemeParentId() {
        return THEME_PARENT_ID;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> saveTheme(String userId, ThemeSaveRequest request) {
        checkRepetitionName(request.getName(), null);
        ThemeResourceEntity entity = getThemeResourceEntitySave(userId, request);
        save(entity);
        return BusinessResult.success(entity.getId());
    }

    public  InternalUserInfoVO getSysUserByUserId(String userId) {
        if (StringUtils.isBlank(userId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000030.getCode());
        }
        CommonResult<InternalUserInfoVO> result = sysUserFeignClient.getUserInfoByUserId(userId);
        if (Objects.isNull(result.getData())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000030.getCode());
        }
        return result.getData();
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<ThemeResult> getThemeInfo(String id) {
        ThemeResourceEntity entity = getById(id);
        ThemeResult result = BeanCopyUtils.copyProperties(entity, new ThemeResult());
        //该主题下的资源分类信息
        List<ThemeResult> resourceList = themeResourceMapper.getResourceByParentId(id);
        result.setResourceList(resourceList);
        return BusinessResult.success(result);
    }

    /**
     * save的情况下id传null，update时候则传主键id
     *
     * @param name
     * @param id
     */
    private void checkRepetitionName(String name, String id) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitName(name, id));
        if (hasExit) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000002.getCode());
        }
    }

    private ThemeResourceEntity getThemeResourceEntitySave(String userId, ThemeSaveRequest request) {
        ThemeResourceEntity entity = BeanCopyUtils.copyProperties(request, new ThemeResourceEntity());
        Date date = new Date();
        entity.setCreateBy(userId);
        entity.setCreateUserId(userId);
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
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> updateTheme(String userId, ThemeUpdateRequest request) {
        checkRepetitionName(request.getName(), request.getThemeId());
        ThemeResourceEntity entity = getById(request.getThemeId());
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(userId);
        entity.setName(request.getName());
        updateById(entity);
        return BusinessResult.success(entity.getId());
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<IdResult> deleteTheme(String userId, ThemeDeleteRequest request) {
        //删除主题下的资源
        List<ThemeResult> resourceList = themeResourceMapper.getResourceByParentId(request.getId());
        if (!CollectionUtils.isEmpty(resourceList)) {
            for (ThemeResult themeResult : resourceList) {
                try {
                    deleteResource(userId, new ResourceDeleteRequest(themeResult.getId()));
                } catch (AppException e) {
                    throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000014.getCode());
                }
            }
        }
        //删除主题，返回主题id
        ThemeResourceEntity entity = getThemeResourceEntityDel(userId, request);
        updateById(entity);
        IdResult idResult = getIdResult(entity.getId(), entity.getSort(), THEME_PARENT_ID);
        return BusinessResult.success(idResult);
    }

    private IdResult getIdResult(String id, Integer sort, String parentId) {
        IdResult result = new IdResult();
        result.setCurrent(id);
        if (!THEME_PARENT_ID.equals(parentId)) {
            result.setThemeId(parentId);
        }
        //查找当前主题或者资源的下一个
        String nextId = themeResourceMapper.selectNextId(sort, parentId);
        if (!StringUtils.isBlank(nextId)) {
            result.setNext(nextId);
            return result;
        }

        //查找当前主题或者资源的第一个
        String previousId = themeResourceMapper.selectPreviousId(sort, parentId);
        result.setPrevious(previousId);
        return result;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> saveResource(String userId, ResourceSaveRequest request) {
        checkRepetitionNameByResource(request.getName(), null);
        ThemeResourceEntity entity = getResourceEntity(userId, request);
        save(entity);
        return BusinessResult.success(entity.getId());
    }

    /**
     * save的情况则id传null
     *
     * @param name
     * @param id
     */
    private void checkRepetitionNameByResource(String name, String id) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitNameByResource(name, id));
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
        long openCount = resourceTableList.stream().filter(entity -> StatusEnum.ENABLE.getCode().equals(entity.getExternalState())).count();
        result.setOpenRate(DoubleUtils.doublePercent(openCount, resourceTableList.size()));
        result.setColumnsCount(resourceTableList.parallelStream().mapToInt(ResourceTableEntity::getColumnsCount).sum());
        result.setTableCount(resourceTableList.size());
        Long dataSizeCount = resourceTableMapper.getCountDataSizeResourceId(id);
        result.setDataSize(dataSizeCount);
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> updateResource(String userId, ResourceUpdateRequest request) {
        checkRepetitionNameByResource(request.getName(), request.getId());
        ThemeResourceEntity entity = getById(request.getId());
        BeanCopyUtils.copyProperties(request, entity);
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(userId);
        updateById(entity);
        return BusinessResult.success(entity.getId());
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<IdResult> deleteResource(String userId, ResourceDeleteRequest request) throws AppException {
        //如果删除的资源分类包含有已开放的表,不能删除
        if (BooleanUtils.isTrue(resourceTableMapper.hasExitExternalStateByResourceIds(new String[]{request.getId()}, StatusEnum.ENABLE.getCode()))) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000013.getCode());
        }
        resourceTableMapper.deleteByResourceId(request.getId());

        //软删除资源分类信息，返回其主题id（前端高亮用）
        ThemeResourceEntity entity = getById(request.getId());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        entity.setDelFlag(DelFalgEnum.HAS_DELETE.getDesc());
        updateById(entity);
        //返回资源id和主题id（前端高亮用）
        IdResult idResult = getIdResult(entity.getId(), entity.getSort(), entity.getParentId());
        return BusinessResult.success(idResult);
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
            ThemeResourceEntity themeEntity = getById(themeId);
            //二级目录拖到一级目录,需要报错
            if (!THEME_PARENT_ID.equals(themeEntity.getParentId())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000015.getCode());
            }
            themeResourceMapper.updateSort(size, themeId);
            size -= 1;
            if (resourceIds.length <= 0) {
                continue;
            }
            //更新资源表排序，并更新资源所属主题
            if (BooleanUtils.isTrue(themeResourceMapper.hasExitErrorLevel(resourceIds, THEME_PARENT_ID))) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.getCode());
            }
            int resourceSize = resourceIds.length;
            for (String resourceId : resourceIds) {
                ThemeResourceEntity resourceEntity = getById(resourceId);
                resourceEntity.setSort(resourceSize);
                resourceEntity.setParentId(themeId);
                updateById(resourceEntity);
                resourceSize -= 1;
            }
        }
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<Boolean> hasExitTheme(ThemeSaveRequest request) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitName(request.getName(), null));
        return BusinessResult.success(hasExit);
    }

    @Override
    public BusinessResult<Boolean> hasExitResource(ResourceSaveRequest request) {
        boolean hasExit = BooleanUtils.isTrue(themeResourceMapper.hasExitNameByResource(request.getName(), null));
        return BusinessResult.success(hasExit);
    }

    @Override
    public BusinessResult<List<ThemeResult>> getList() {
        List<ThemeResult> list = themeResourceMapper.getResourceByParentId(THEME_PARENT_ID);
        if (CollectionUtils.isEmpty(list)) {
            return BusinessResult.success(list);
        }
        for (ThemeResult themeResult : list) {
            Boolean themeHidden = resourceTableService.hasExistOpenExternalState(new ExistOpenExternalRequest(themeResult.getId(), null));
            themeResult.setHidden(themeHidden);
            List<ThemeResult> resourceByParentId = themeResourceMapper.getResourceByParentId(themeResult.getId());
            if (!CollectionUtils.isEmpty(resourceByParentId)){
                for (ThemeResult resourceResult : resourceByParentId) {
                    Boolean resourceHidden = resourceTableService.hasExistOpenExternalState(new ExistOpenExternalRequest(null,
                            resourceResult.getId()));
                    resourceResult.setHidden(resourceHidden);
                }
            }
            themeResult.setResourceList(resourceByParentId);
        }
        return BusinessResult.success(list);
    }

    private ThemeResourceEntity getResourceEntity(String userId, ResourceSaveRequest request) {
        ThemeResourceEntity themeEntity = getById(request.getId());
        if (!ThemeResourceServiceImpl.getThemeParentId().equals(themeEntity.getParentId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000024.getCode());
        }
        if (DelFalgEnum.HAS_DELETE.getDesc().equals(themeEntity.getDelFlag())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000019.getCode());
        }
        ThemeResourceEntity entity = BeanCopyUtils.copyProperties(request, new ThemeResourceEntity());
        Date date = new Date();
        entity.setCreateBy(userId);
        entity.setCreateUserId(userId);
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
        ThemeResourceEntity entity = getById(request.getId());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        entity.setDelFlag(DelFalgEnum.HAS_DELETE.getDesc());
        return entity;
    }
}
