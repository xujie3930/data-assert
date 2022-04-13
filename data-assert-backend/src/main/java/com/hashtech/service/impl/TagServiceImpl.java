package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.TagMapper;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.TagService;
import com.hashtech.utils.CharUtil;
import com.hashtech.utils.DateUtils;
import com.hashtech.utils.RandomUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.TagRelateResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CompanyTagService companyTagService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private OauthApiService oauthApiService;
    @Override
    public Boolean hasExistCode(String code) {
        boolean hasExistCode = BooleanUtils.isTrue(tagMapper.hasExistCode(code));
        return hasExistCode;
    }

    @Override
    public Boolean hasExistName(String name, String id) {
        boolean hasExistName = BooleanUtils.isTrue(tagMapper.hasExistName(name, id));
        return hasExistName;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDef(String userId, TagSaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkLegalName(request.getName());
        Date date = new Date();
        if (hasExistCode(request.getCode())){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000003.getCode());
        }
        if (hasExistName(request.getName(), null)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000004.getCode());
        }
        TagEntity tagEntity = BeanCopyUtils.copyProperties(request, new TagEntity());
        tagEntity.setCreateUserId(userId);
        tagEntity.setCreateBy(user.getUsername());
        tagEntity.setUpdateUserId(userId);
        tagEntity.setUpdateBy(user.getUsername());
        tagEntity.setCreateTime(date);
        tagEntity.setUpdateTime(date);
        //TODO:后续统计企业的标签数量，有必要时候改为redis的sortsSets
        tagEntity.setUsedTime(0);
        save(tagEntity);
        return true;
    }

    @Override
    public String getTagCode(Integer len) {
        String code = RandomUtils.getRandomWithNumber(len);
        while (hasExistCode(code)){
            code = RandomUtils.getRandomWithNumber(len);
        }
        return code;
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDef(String userId, TagUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkLegalName(request.getName());
        Date date = new Date();
        TagEntity tagEntity = findById(request.getId());
        if (Objects.isNull(tagEntity)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000007.getCode());
        }
        if (hasExistName(request.getName(), request.getId())){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000004.getCode());
        }
        tagEntity.setName(request.getName());
        tagEntity.setDescribe(request.getDescribe());
        tagEntity.setState(request.getState());
        tagEntity.setUpdateUserId(userId);
        tagEntity.setUpdateTime(date);
        tagEntity.setUpdateBy(user.getUsername());
        saveOrUpdate(tagEntity);
        return true;
    }

    @Override
    public BusinessPageResult getList(TagListRequest request) {
        Wrapper<TagEntity> wrapper = queryWrapper(request);
        IPage<TagEntity> page = this.page(
                new Query<TagEntity>().getPage(request),
                wrapper
        );
        return BusinessPageResult.build(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean enOrDisable(String userId, TagChangeStateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        TagEntity entity = findById(request.getId());
        if (Objects.isNull(entity)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000007.getCode());
        }
        entity.setState(request.getState());
        entity.setUpdateUserId(userId);
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(user.getUsername());
        saveOrUpdate(entity);
        //停用标签时，企业所打的标签也会删除该停用的标签
        List<CompanyTagEntity> companyTagList = companyTagService.getLitsByTagId(request.getId());
        if (!CollectionUtils.isEmpty(companyTagList)){
            Date date = new Date();
            for (CompanyTagEntity companyTagEntity : companyTagList) {
                companyTagEntity.setUpdateTime(date);
                companyTagEntity.setUpdateUserId(userId);
                companyTagEntity.setUpdateBy(user.getUsername());
                companyTagEntity.setDelFlag(DelFalgStateEnum.HAS_DELETE.getCode());
            }
        }
        companyTagService.saveOrUpdateBatch(companyTagList);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDef(String userId, String[] ids) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        if (ids.length <= 0) {
            return true;
        }
        //删除标签
        deleteTag(user, ids);
        //删除标签企业
        deleteCompanyTagByTagIds(user, Arrays.asList(ids));
        return true;
    }

    private void deleteCompanyTagByTagIds(InternalUserInfoVO user, List<String> ids) {
        companyTagService.deleteCompanyTagByTagIds(user.getUserId(), ids);
    }

    private void deleteTag(InternalUserInfoVO user, String[] ids) {
        Long delCount = tagMapper.selectCountByStateAndIds(TagStateEnum.ENABLE.getCode(), ids);
        if (delCount > 0) {
            if (ids.length == 1) {
                throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000009.getCode());
            }
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000010.getCode());
        }
        //变更资源表状态
        List<TagEntity> list = new ArrayList<>();
        for (String id : ids) {
            TagEntity entity = findById(id);
            if (Objects.isNull(entity)){
                continue;
            }
            entity.setUpdateTime(new Date());
            entity.setUpdateUserId(user.getUserId());
            entity.setUpdateBy(user.getUsername());
            list.add(entity);
        }
        saveOrUpdateBatch(list);
        removeByIds(Arrays.asList(ids));
    }

    @Override
    public TagEntity detailById(String id) {
        return tagMapper.findById(id);
    }

    @Override
    public List<TagEntity> getListWithoutPaging() {
        return tagMapper.getListWithoutPaging();
    }

    @Override
    public List<TagEntity> getByCompanyId(String id) {
        return tagMapper.getByCompanyId(id);
    }

    @Override
    public TagRelateResult relate(CompanyListRequest request) {
        TagRelateResult result = new TagRelateResult();
        TagEntity tagEntity = findById(request.getTagId());
        if (Objects.isNull(tagEntity)) {
            return result;
        }
        result.setTagEntity(tagEntity);
        request.setTagNum("desc");
        BusinessPageResult pageResult = companyInfoService.getList(request);
        result.setBusinessPageResult(pageResult);
        return result;
    }

    @Override
    public void updateUsedTimeById(Long count, String tagId) {
        tagMapper.updateUsedTimeById(count, tagId);
    }

    private Wrapper<TagEntity> queryWrapper(TagListRequest request) {
        QueryWrapper<TagEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(TagEntity.DEL_FLAG, DelFlagEnum.ENA_BLED.getCode());
        wrapper.orderByDesc(TagEntity.UPDATE_TIME);
        if (StringUtils.isNotBlank(request.getName())) {
            wrapper.like(TagEntity.NAME, request.getName());
        }
        if (StringUtils.isNotBlank(request.getUpdateBy())) {
            wrapper.like(TagEntity.UPDATE_BY, request.getUpdateBy());
        }
        if (null != request.getState()){
            wrapper.eq(TagEntity.STATE, request.getState());
        }
        if (StringUtils.isNotBlank(request.getLastUsedTimeBegin()) && StringUtils.isNotBlank(request.getLastUsedTimeEnd())){
            Date startTime = DateUtils.parseDate(request.getLastUsedTimeBegin() + " 00:00:00");
            Date endTime = DateUtils.parseDate(request.getLastUsedTimeEnd() + " 23:59:59");
            wrapper.ge(TagEntity.LAST_USED_TIME, startTime).le(TagEntity.LAST_USED_TIME, endTime);
        }
        if ("updateTime".equals(request.getSortField()) && "asc".equals(request.getSort())){
            wrapper.orderByAsc(TagEntity.UPDATE_TIME);
        }
        if ("updateTime".equals(request.getSortField()) && "desc".equals(request.getSort())){
            wrapper.orderByDesc(TagEntity.UPDATE_TIME);
        }
        if ("lastUsedTime".equals(request.getSortField()) && "asc".equals(request.getSort())){
            wrapper.orderByAsc(TagEntity.LAST_USED_TIME);
        }
        if ("lastUsedTime".equals(request.getSortField()) && "desc".equals(request.getSort())){
            wrapper.orderByDesc(TagEntity.LAST_USED_TIME);
        }
        return wrapper;
    }

    private void checkLegalName(String name) {
        if (CharUtil.isSpecialChar(name)) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000008.getCode());
        }
    }

    public TagEntity findById(String id) {
        return tagMapper.findById(id);
    }

}
