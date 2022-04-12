package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.mapper.CompanyInfoMapper;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.TagService;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.result.CompanyListResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfoEntity> implements CompanyInfoService {

    @Autowired
    private CompanyTagService companyTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDef(String userId, CompanySaveRequest request) {
        Date date = new Date();
        CompanyInfoEntity companyInfoEntity = saveCompanyInfo(userId, request, date);
        String companyInfoId = companyInfoEntity.getId();
        if (!CollectionUtils.isEmpty(request.getTagIds())){
            for (String tagId : request.getTagIds()) {
                saveCompanyTag(userId, date, companyInfoEntity, companyInfoId, tagId);
                tagService.updateUsedTime();
            }
        }
        return true;
    }

    @Override
    public Boolean hasExistUscc(String uscc) {
        boolean hasExistUscc= BooleanUtils.isTrue(companyInfoMapper.hasExistUscc(uscc));
        return hasExistUscc;
    }

    @Override
    public BusinessPageResult<CompanyListResult> getList(CompanyListRequest request) {
        List<String> companyIdList = new ArrayList<>();
        if (null != request.getTagId()){
            List<CompanyTagEntity> companyTagList = companyTagService.getLitsByTagId(request.getTagId());
            if (!CollectionUtils.isEmpty(companyTagList)){
                companyIdList = companyTagList.stream().map(o -> o.getCompanyInfoId()).collect(Collectors.toList());
            }
        }
        Wrapper<CompanyInfoEntity> wrapper = queryWrapper(request, companyIdList);
        IPage<CompanyInfoEntity> page = this.page(
                new Query<CompanyInfoEntity>().getPage(request),
                wrapper
        );
        IPage<CompanyListResult> resultIPage = new Page<>();
        List<CompanyInfoEntity> records = page.getRecords();
        List<CompanyListResult> companyListResults = new LinkedList<>();
        for (CompanyInfoEntity record : records) {
            CompanyListResult companyListResult = BeanCopyUtils.copyProperties(record, new CompanyListResult());
            List<TagEntity> tagListByCompanyId = tagService.getByCompanyId(record.getId());
            if (!CollectionUtils.isEmpty(tagListByCompanyId)){
                List<Map<String, String>> list = new LinkedList<>();
                for (TagEntity tagEntity : tagListByCompanyId) {
                    Map<String, String> map = new HashMap<>();
                    map.put("tagId", tagEntity.getId());
                    map.put("tagName", tagEntity.getName());
                    list.add(map);
                }
                companyListResult.setTagMap(list);
            }
            companyListResults.add(companyListResult);
        }
        resultIPage.setRecords(companyListResults);
        resultIPage.setPages(page.getPages());
        resultIPage.setTotal(page.getTotal());
        return BusinessPageResult.build(resultIPage, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCompany(String userId, String[] ids) {
        if (ids.length <= 0) {
            return true;
        }
        //变更资源表状态
        List<CompanyInfoEntity> list = new ArrayList<>();
        for (String id : ids) {
            CompanyInfoEntity entity = getById(id);
            if (Objects.isNull(entity)){
                continue;
            }
            entity.setUpdateTime(new Date());
            entity.setUpdateUserId(userId);
            entity.setDelFlag(DelFalgStateEnum.HAS_DELETE.getCode());
            list.add(entity);
        }
        saveOrUpdateBatch(list);
        removeByIds(Arrays.asList(ids));
        return true;
    }

    private Wrapper<CompanyInfoEntity> queryWrapper(CompanyListRequest request, List<String> companyIdList) {
        QueryWrapper<CompanyInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(CompanyInfoEntity.DEL_FLAG, DelFlagEnum.ENA_BLED.getCode());
        if (!StringUtils.isBlank(request.getUscc())) {
            wrapper.like(CompanyInfoEntity.USCC, request.getUscc());
        }
        if (!StringUtils.isBlank(request.getCorpNm())) {
            wrapper.like(CompanyInfoEntity.CORP_NM, request.getCorpNm());
        }
        if (!CollectionUtils.isEmpty(companyIdList)){
            wrapper.in(CompanyInfoEntity.ID, companyIdList);
        }
        if (StringUtils.isNotBlank(request.getUpdateTime()) && "desc".equals(request.getUpdateTime())){
            wrapper.orderByDesc(CompanyInfoEntity.UPDATE_TIME);
        }
        if (StringUtils.isNotBlank(request.getTagNum()) && "desc".equals(request.getTagNum())){
            wrapper.orderByDesc(CompanyInfoEntity.TAG_NUM);
        }
        return wrapper;
    }


    @NotNull
    private CompanyInfoEntity saveCompanyInfo(String userId, CompanySaveRequest request, Date date) {
        CompanyInfoEntity companyInfoEntity = BeanCopyUtils.copyProperties(request, new CompanyInfoEntity());
        companyInfoEntity.setCreateTime(date);
        companyInfoEntity.setCreateUserId(userId);
        companyInfoEntity.setUpdateTime(date);
        companyInfoEntity.setUpdateUserId(userId);
        companyInfoEntity.setTagNum(request.getTagIds().size());
        save(companyInfoEntity);
        return companyInfoEntity;
    }

    private void saveCompanyTag(String userId, Date date, CompanyInfoEntity companyInfoEntity, String companyInfoId, String tagId) {
        CompanyTagEntity companyTagEntity = new CompanyTagEntity();
        companyTagEntity.setTagId(tagId);
        companyTagEntity.setCompanyInfoId(companyInfoId);
        companyInfoEntity.setCreateTime(date);
        companyInfoEntity.setCreateUserId(userId);
        companyInfoEntity.setUpdateTime(date);
        companyInfoEntity.setUpdateUserId(userId);
        companyTagService.save(companyTagEntity);
    }
}
