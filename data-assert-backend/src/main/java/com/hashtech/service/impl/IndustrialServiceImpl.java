package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.ImmutableMap;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.entity.IndustrialCompanyEntity;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.IndustrialMapper;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.IndustrialCompanyService;
import com.hashtech.service.IndustrialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.service.OauthApiService;
import com.hashtech.utils.CharUtil;
import com.hashtech.web.request.IndustryListRequest;
import com.hashtech.web.request.IndustrySaveRequest;
import com.hashtech.web.request.IndustryUpdateRequest;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 产业库 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
@Service
public class IndustrialServiceImpl extends ServiceImpl<IndustrialMapper, IndustrialEntity> implements IndustrialService {

    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private IndustrialMapper industrialMapper;
    @Autowired
    private IndustrialCompanyService industrialCompanyService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public String saveDef(String userId, IndustrySaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkName(request.getName());
        checkRepetitionName(request.getName(), null);
        IndustrialEntity entitySave = getEntitySave(user, request);
        save(entitySave);
        return entitySave.getId();
    }

    private IndustrialEntity getEntitySave(InternalUserInfoVO user, IndustrySaveRequest request) {
        IndustrialEntity entity = BeanCopyUtils.copyProperties(request, new IndustrialEntity());
        Date date = new Date();
        entity.setCreateUserId(user.getUserId());
        entity.setCreateBy(user.getUsername());
        entity.setCreateTime(date);
        entity.setUpdateUserId(user.getUserId());
        entity.setUpdateBy(user.getUsername());
        entity.setUpdateTime(date);
        return entity;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public String updateDef(String userId, IndustryUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        IndustrialEntity industrialEntity = getById(request.getId());
        if (Objects.isNull(industrialEntity)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000028.getCode());
        }
        checkName(request.getName());
        checkRepetitionName(request.getName(), request.getId());
        IndustrialEntity entityUpdate = getEntityUpdate(request, user, industrialEntity);
        updateById(entityUpdate);
        return request.getId();
    }

    private IndustrialEntity getEntityUpdate(IndustryUpdateRequest request, InternalUserInfoVO user, IndustrialEntity industrialEntity) {
        BeanCopyUtils.copyProperties(request, industrialEntity);
        industrialEntity.setUpdateTime(new Date());
        industrialEntity.setUpdateUserId(user.getUserId());
        industrialEntity.setUpdateBy(user.getUsername());
        return industrialEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(String userId, String id) {
        List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByIndustrialId(id);
        //根据所有的companyId去company_info表删除
        Set<String> companyIdSet = industrialCompanyList.stream().map(IndustrialCompanyEntity::getCompanyInfoId).collect(Collectors.toSet());
        //删除该产业库下所有企业
        ImmutableMap<String, String[]> map = ImmutableMap.<String, String[]>builder()
                .put(id, companyIdSet.toArray(new String[companyIdSet.size()]))
                .build();
        companyInfoService.deleteCompanyDef(userId, map);
        //删除产业信息
        removeById(id);
        return id;
    }

    @Override
    public List<IndustrialEntity> likeByName(String name) {
        return industrialMapper.likeByName(name);
    }

    @Override
    public BusinessPageResult<IndustrialEntity> getList(IndustryListRequest request) {
        Wrapper<IndustrialEntity> wrapper = queryWrapper(request);
        IPage<IndustrialEntity> page = this.page(
                new Query<IndustrialEntity>().getPage(request),
                wrapper
        );
        return BusinessPageResult.build(page, request);
    }

    @Override
    public Boolean hasExistName(String name, String id) {
        boolean hasExistName = BooleanUtils.isTrue(industrialMapper.hasExitName(name, id));
        return hasExistName;
    }

    private Wrapper<IndustrialEntity> queryWrapper(IndustryListRequest request) {
        QueryWrapper<IndustrialEntity> wrapper = new QueryWrapper<>();
        //TODO:先暂时这样判断，以后优化
        List<IndustrialEntity> industrialEntityList = industrialCompanyService.selectByRequest(request);
        if (!CollectionUtils.isEmpty(industrialEntityList)){
            List<String> industrialIds = industrialEntityList.stream().map(IndustrialEntity::getId).collect(Collectors.toList());
            wrapper.in(IndustrialEntity.ID, industrialIds);
        }else {
            wrapper.in(IndustrialEntity.ID, -1);
        }
        wrapper.eq(IndustrialEntity.DEL_FLAG, DelFlagEnum.ENA_BLED.getCode());
        wrapper.orderByDesc(IndustrialEntity.CREATE_TIME);
        if (StringUtils.isNotBlank(request.getIndustrialName())){
            wrapper.like(IndustrialEntity.NAME, request.getIndustrialName());
        }
        return wrapper;
    }


    /**
     * 检查重复名称
     * @param name
     * @param id
     */
    private void checkRepetitionName(String name, String id) {
        boolean hasExit = BooleanUtils.isTrue(industrialMapper.hasExitName(name, id));
        if (hasExit) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000026.getCode());
        }
    }

    /**
     * 检查特殊字符
     * @param name
     */
    private void checkName(String name) {
        if (CharUtil.isSpecialChar(name)) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000027.getCode());
        }
    }
}
