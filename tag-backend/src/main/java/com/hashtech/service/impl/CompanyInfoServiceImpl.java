package com.hashtech.service.impl;

import com.hashtech.common.BeanCopyUtils;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.mapper.CompanyInfoMapper;
import com.hashtech.mapper.CompanyTagMapper;
import com.hashtech.service.CompanyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.TagService;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.TagSaveRequest;
import org.apache.commons.lang.BooleanUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;

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

    @NotNull
    private CompanyInfoEntity saveCompanyInfo(String userId, CompanySaveRequest request, Date date) {
        CompanyInfoEntity companyInfoEntity = BeanCopyUtils.copyProperties(request, new CompanyInfoEntity());
        companyInfoEntity.setCreateTime(date);
        companyInfoEntity.setCreateUserId(userId);
        companyInfoEntity.setUpdateTime(date);
        companyInfoEntity.setUpdateUserId(userId);
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
