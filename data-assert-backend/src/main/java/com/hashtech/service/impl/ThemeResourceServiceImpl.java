package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.FileParse;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.MasterDataEntity;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.feign.ServeFeignClient;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.MasterDataMapper;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.utils.DoubleUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.IdResult;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class ThemeResourceServiceImpl extends ServiceImpl<ThemeResourceMapper, ThemeResourceEntity> implements ThemeResourceService {

    private static final String THEME_PARENT_ID = "0";
    @Autowired
    private ThemeResourceMapper themeResourceMapper;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private FileParse fileParse;
    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private ServeFeignClient serveFeignClient;
    @Autowired
    private MasterDataMapper masterDataMapper;

    public static String getThemeParentId() {
        return THEME_PARENT_ID;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<String> saveTheme(String userId, ThemeSaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkRepetitionName(request.getName(), null);
        ThemeResourceEntity entity = getThemeResourceEntitySave(user, request);
        save(entity);
        return BusinessResult.success(entity.getId());
    }

    /*public  InternalUserInfoVO getSysUserByUserId(String userId) {
        if (StringUtils.isBlank(userId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000030.getCode());
        }
        CommonResult<InternalUserInfoVO> result = sysUserFeignClient.getUserInfoByUserId(userId);
        if (Objects.isNull(result.getData())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000030.getCode());
        }
        return result.getData();
    }*/

    @Override
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

    private ThemeResourceEntity getThemeResourceEntitySave(InternalUserInfoVO user, ThemeSaveRequest request) {
        ThemeResourceEntity entity = BeanCopyUtils.copyProperties(request, new ThemeResourceEntity());
        Date date = new Date();
        entity.setCreateBy(user.getUsername());
        entity.setCreateUserId(user.getUserId());
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setUpdateBy(user.getUsername());
        entity.setParentId(THEME_PARENT_ID);
        //TODO:查询和插入得保证原子性
        Integer maxSort = themeResourceMapper.getMaxSort();
        entity.setSort(maxSort + 1);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<String> updateTheme(String userId, ThemeUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkRepetitionName(request.getName(), request.getThemeId());
        ThemeResourceEntity entity = getById(request.getThemeId());
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(user.getUsername());
        entity.setName(request.getName());
        updateById(entity);
        return BusinessResult.success(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<IdResult> deleteTheme(String userId, ThemeDeleteRequest request) {
        //主数据类型的主题不能删除
        List<MasterDataEntity> masterDataList = masterDataMapper.getList();
        List<String> mDatathemeIds = masterDataList.stream().map(d -> d.getThemeId()).collect(Collectors.toList());
        if (mDatathemeIds.contains(request.getId())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000041.getCode());
        }
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        BusinessResult<Map<String, List<String>>> result = serveFeignClient.getOpenTopicAndClassifyIds();
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000038.getCode());
        }
        Map<String, List<String>> map = result.getData();
        //所有在开放平台开放的主题
        List<String> openTopicIds = map.get("topicIds");
        if (openTopicIds.contains(request.getId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000014.getCode());
        }
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
        ThemeResourceEntity entity = getThemeResourceEntityDel(user, request);
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
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<String> saveResource(String userId, ResourceSaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkRepetitionNameByResource(request.getName(), null);
        ThemeResourceEntity entity = getResourceEntity(user, request);
        save(entity);
        //按开放平台保存图片格式
        String picUrl = fileParse.uploadFile(entity.getId(), request.getPicUrl());
        entity.setPicUrl(picUrl);
        updateById(entity);
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
    public BusinessResult<ResourceResult> getResourceInfo(String id) {
        ResourceResult result = new ResourceResult();
        ThemeResourceEntity resourceEntity = getById(id);
        if (resourceEntity == null) {
            return BusinessResult.success(result);
        }
        BeanCopyUtils.copyProperties(resourceEntity, result);
        List<ResourceTableEntity> resourceTableList = resourceTableMapper.getListByResourceId(id);
        BusinessResult<String[]> serveResult = serveFeignClient.getOpenDirIds(id);
        if (!serveResult.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000038.getCode());
        }
        long openCount = serveResult.getData().length;
        result.setOpenRate(DoubleUtils.doublePercent(openCount, resourceTableList.size()));
        result.setColumnsCount(resourceTableList.parallelStream().mapToInt(ResourceTableEntity::getColumnsCount).sum());
        result.setTableCount(resourceTableList.size());
        Long dataSizeCount = resourceTableMapper.getCountDataSizeResourceId(id);
        result.setDataSize(dataSizeCount);
        return BusinessResult.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<String> updateResource(String userId, ResourceUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkRepetitionNameByResource(request.getName(), request.getId());
        ThemeResourceEntity entity = getById(request.getId());
        BeanCopyUtils.copyProperties(request, entity);
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(user.getUsername());
        String picUrl = fileParse.uploadFile(request.getId(), request.getPicUrl());
        entity.setPicUrl(picUrl);
        updateById(entity);
        return BusinessResult.success(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<IdResult> deleteResource(String userId, ResourceDeleteRequest request) throws AppException {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        //如果删除的资源分类包含有已开放的表,不能删除
        BusinessResult<Map<String, List<String>>> result = serveFeignClient.getOpenTopicAndClassifyIds();
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000038.getCode());
        }
        Map<String, List<String>> map = result.getData();
        //所有在开放平台开放的主题
        //所有在开放平台开放的资源分类
        List<String> openResourceIds = map.get("classifyIds");
        if (openResourceIds.contains(request.getId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000013.getCode());
        }
        resourceTableMapper.deleteByResourceId(request.getId());

        //软删除资源分类信息，返回其主题id（前端高亮用）
        ThemeResourceEntity entity = getById(request.getId());
        entity.setUpdateBy(user.getUsername());
        entity.setUpdateTime(new Date());
        entity.setDelFlag(DelFalgEnum.HAS_DELETE.getDesc());
        updateById(entity);
        //返回资源id和主题id（前端高亮用）
        IdResult idResult = getIdResult(entity.getId(), entity.getSort(), entity.getParentId());
        return BusinessResult.success(idResult);
    }

    @Override
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
            //更新该资源对应的主题
            resourceTableService.updateThemIdByResourceIds(themeId, resourceIds);
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
        List<ThemeResult> list = themeResourceMapper.getResourceByParentId(null);
        if (CollectionUtils.isEmpty(list)) {
            return BusinessResult.success(list);
        }
        List<ThemeResult> themeList = list.stream()
                .filter(t -> THEME_PARENT_ID.equals(t.getParentId()))
                .collect(Collectors.toList());
        for (ThemeResult themeResult : themeList) {
            List<ThemeResult> resourceList = list.stream()
                    .filter(t -> themeResult.getId().equals(t.getParentId()))
                    .collect(Collectors.toList());
            themeResult.setResourceList(resourceList);
        }
        return BusinessResult.success(themeList);
    }

    @Override
    public Boolean masterDataJudge(MasterDataJudgeRequest request) {
        String themeId = request.getThemeId();
        if (StringUtils.isNotBlank(request.getResourceId()) && StringUtils.isBlank(request.getThemeId())){
            ThemeResourceEntity themeResourceEntity = themeResourceMapper.selectById(request.getResourceId());
            themeId = themeResourceEntity.getParentId();
        }
        MasterDataEntity masterDataEntity = masterDataMapper.selectByThemeId(themeId);
        return Objects.isNull(masterDataEntity);
    }

    private ThemeResourceEntity getResourceEntity(InternalUserInfoVO user, ResourceSaveRequest request) {
        ThemeResourceEntity themeEntity = getById(request.getId());
        if (!ThemeResourceServiceImpl.getThemeParentId().equals(themeEntity.getParentId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000024.getCode());
        }
        if (DelFalgEnum.HAS_DELETE.getDesc().equals(themeEntity.getDelFlag())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000019.getCode());
        }
        ThemeResourceEntity entity = BeanCopyUtils.copyProperties(request, new ThemeResourceEntity());
        Date date = new Date();
        entity.setId(null);
        entity.setParentId(request.getId());
        entity.setCreateBy(user.getUsername());
        entity.setCreateUserId(user.getUserId());
        entity.setUpdateBy(user.getUsername());
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        //TODO:查询和插入得保证原子性
        Integer maxSort = themeResourceMapper.getMaxSortByParentId(request.getId());
        entity.setSort(maxSort + 1);
        entity.setPicUrl(null);
        return entity;
    }

    private ThemeResourceEntity getThemeResourceEntityDel(InternalUserInfoVO user, ThemeDeleteRequest request) {
        ThemeResourceEntity entity = getById(request.getId());
        entity.setUpdateBy(user.getUsername());
        entity.setUpdateTime(new Date());
        entity.setDelFlag(DelFalgEnum.HAS_DELETE.getDesc());
        return entity;
    }
}
