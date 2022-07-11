package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.DelFalgStateEnum;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.CompanyTagMapper;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.TagService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 企业标签关联表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Service
public class CompanyTagServiceImpl extends ServiceImpl<CompanyTagMapper, CompanyTagEntity> implements CompanyTagService {

    @Autowired
    private CompanyTagMapper companyTagMapper;
    @Autowired
    private TagService tagService;

    @Override
    public List<CompanyTagEntity> getLitsByTagId(String tagId) {
        return companyTagMapper.getLitsByTagId(tagId);
    }

    @Override
    public CompanyTagEntity findByTagIdAndCompanyId(String tagId, String companyId) {
        return companyTagMapper.findByTagIdAndCompanyId(tagId, companyId);
    }

    @Override
    public List<CompanyTagEntity> getListByCompanyId(String companyInfoId) {
        return companyTagMapper.getListByCompanyId(companyInfoId);
    }

    @Override
    public List<Map<String, Object>> countByTagId() {
        return companyTagMapper.countByTagId();
    }

    @Override
    public List<Map<String, Object>> countBycompanyInfoId() {
        return companyTagMapper.countBycompanyInfoId();
    }

    @Override
    public void deleteCompanyTagByTagIds(String userId, List<String> ids) {
        companyTagMapper.deleteCompanyTagByTagIds(userId, ids);
    }

    @Override
    public void deleteCompanyTagByCompanyId(String userId, List<String> companyIds) {
        companyTagMapper.deleteCompanyTagByCompanyId(userId, companyIds);
    }

    @Override
    public void deleteCompanyTagBatch(InternalUserInfoVO user, Date date, String companyId, List<String> tagIds) {
        if (StringUtils.isEmpty(companyId) || CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        List<CompanyTagEntity> companyTagUpdateList= new ArrayList<>();
        List<TagEntity> tagUpdateList= new ArrayList<>();
        List<CompanyTagEntity> companyTagEntityList = getListByCompanyId(companyId);
        List<TagEntity> tagEntityList = tagService.selectByIds(tagIds);
        for (String tagId : tagIds) {
            CompanyTagEntity companyTagEntity = companyTagEntityList.stream().filter(i -> tagId.equals(i.getTagId())).findFirst().get();
            companyTagEntity.setUpdateTime(date);
            companyTagEntity.setUpdateUserId(user.getUserId());
            companyTagEntity.setUpdateBy(user.getUsername());
            companyTagEntity.setDelFlag(DelFalgStateEnum.HAS_DELETE.getCode());
            companyTagUpdateList.add(companyTagEntity);
            TagEntity tagEntity = tagEntityList.stream().filter(i -> tagId.equals(i.getId())).findFirst().get();
            tagEntity.setUsedTime(tagEntity.getUsedTime() - 1);
            tagUpdateList.add(tagEntity);
        }
        updateBatchById(companyTagUpdateList);
        //标签关联企业数量-1
        tagService.updateBatchById(tagUpdateList);
    }

    @Override
    public void saveOrUpdateBatchDef(InternalUserInfoVO user, Date date, String companyInfoId, List<String> tagIds) {
        if (StringUtils.isEmpty(companyInfoId) || CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        // 删除旧纪录
        List<CompanyTagEntity> oldCompanyTag = getListByCompanyId(companyInfoId);
        List<String> oldTagIds = oldCompanyTag.stream().map(old -> old.getTagId()).collect(Collectors.toList());
        oldTagIds.removeAll(tagIds);
        deleteCompanyTagBatch(user, date, companyInfoId, oldTagIds);
        // 新增或更新
        List<CompanyTagEntity> companyTagUpdateList= new ArrayList<>();
        List<TagEntity> tagUpdateList= new ArrayList<>();
        List<CompanyTagEntity> companyTagEntityList = getListByCompanyId(companyInfoId);
        List<TagEntity> tagEntitiesList = tagService.selectByIds(tagIds);
        for (TagEntity tagEntity : tagEntitiesList) {
            CompanyTagEntity companyTagEntity;
            Optional<CompanyTagEntity> optional = companyTagEntityList.stream().filter(i -> tagEntity.getId().equals(i.getTagId())).findFirst();
            if (!optional.isPresent()) {
                companyTagEntity = new CompanyTagEntity();
                companyTagEntity.setCreateTime(date);
                companyTagEntity.setCreateUserId(user.getUserId());
                companyTagEntity.setCreateBy(user.getUsername());
                companyTagEntity.setTagId(tagEntity.getId());
                companyTagEntity.setCompanyInfoId(companyInfoId);
                tagEntity.setUsedTime(tagEntity.getUsedTime() + 1);
            }else {
                companyTagEntity = optional.get();
            }
            companyTagEntity.setUpdateTime(date);
            companyTagEntity.setUpdateUserId(user.getUserId());
            companyTagEntity.setUpdateBy(user.getUsername());
            companyTagUpdateList.add(companyTagEntity);
            tagEntity.setLastUsedTime(date);
            tagUpdateList.add(tagEntity);
        }
        saveOrUpdateBatch(companyTagUpdateList);
        tagService.saveOrUpdateBatch(tagUpdateList);
    }
}
