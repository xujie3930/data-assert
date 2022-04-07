package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.TagEntity;
import com.hashtech.mapper.TagMapper;
import com.hashtech.service.TagService;
import com.hashtech.utils.CharUtil;
import com.hashtech.utils.DateUtils;
import com.hashtech.utils.RandomUtils;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.request.TagSaveRequest;
import com.hashtech.web.request.TagUpdateRequest;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

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
        tagEntity.setUpdateUserId(userId);
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

    private Wrapper<TagEntity> queryWrapper(TagListRequest request) {
        QueryWrapper<TagEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(TagEntity.DEL_FLAG, DelFlagEnum.ENA_BLED.getCode());
        wrapper.orderByDesc(TagEntity.UPDATE_TIME);
        if (StringUtils.isNotBlank(request.getName())){
            wrapper.like(TagEntity.NAME, request.getName());
        }
        if (StringUtils.isNotBlank(request.getUpdateBy())){
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
        if (StringUtils.isNotBlank(request.getSort()) && "asc".equals(request.getSort())){
            wrapper.orderByAsc(TagEntity.UPDATE_TIME);
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
