package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.easyexcel.bean.CompanyInfoImportContent;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.CompanyInfoMapper;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.TagService;
import com.hashtech.utils.CharUtil;
import com.hashtech.utils.excel.ExcelUtils;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.CompanyUpdateRequest;
import com.hashtech.web.result.CompanyListResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private OauthApiServiceImpl oauthApiService;
    private static final String NULL_COMPANY_ID = "0";

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDef(String userId, CompanySaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        if (hasExistUscc(request.getUscc(), null)) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000016.getCode());
        }
        Date date = new Date();
        CompanyInfoEntity companyInfoEntity = saveCompanyInfo(user, request, date);
        String companyInfoId = companyInfoEntity.getId();
        if (!CollectionUtils.isEmpty(request.getTagIds())) {
            for (String tagId : request.getTagIds()) {
                saveCompanyTag(user, date, companyInfoId, tagId);
            }
        }
        return true;
    }

    @Override
    public Boolean hasExistUscc(String uscc, String id) {
        boolean hasExistUscc = BooleanUtils.isTrue(companyInfoMapper.hasExistUscc(uscc, id));
        return hasExistUscc;
    }

    @Override
    public BusinessPageResult<CompanyListResult> getList(CompanyListRequest request) {
        List<String> companyIdList = Arrays.asList(NULL_COMPANY_ID);
        if (null != request.getTagId()) {
            List<CompanyTagEntity> companyTagList = companyTagService.getLitsByTagId(request.getTagId());
            if (!CollectionUtils.isEmpty(companyTagList)) {
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
            if (!CollectionUtils.isEmpty(tagListByCompanyId)) {
                List<String> tagIdList = tagListByCompanyId.stream().map(o -> o.getId()).collect(Collectors.toList());
                companyListResult.setTagMap(tagIdList);
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
    public Boolean deleteCompanyDef(String userId, String[] ids) {
        if (ids.length <= 0) {
            return true;
        }
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        //变更资源表状态
        deleteCompany(user, ids);
        deleteCompanyTagByCompanyId(user, Arrays.asList(ids));
        return true;
    }

    private void deleteCompany(InternalUserInfoVO user, String[] ids) {
        List<CompanyInfoEntity> list = new ArrayList<>();
        for (String id : ids) {
            CompanyInfoEntity entity = getById(id);
            if (Objects.isNull(entity)) {
                continue;
            }
            entity.setUpdateTime(new Date());
            entity.setUpdateUserId(user.getUserId());
            entity.setUpdateBy(user.getUsername());
            entity.setDelFlag(DelFalgStateEnum.HAS_DELETE.getCode());
            list.add(entity);
        }
        saveOrUpdateBatch(list);
        removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDef(String userId, CompanyUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        if (hasExistUscc(request.getUscc(), request.getId())) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000016.getCode());
        }
        CompanyInfoEntity companyInfoEntity = companyInfoMapper.findById(request.getId());
        if (Objects.isNull(companyInfoEntity)) {
            return true;
        }
        //更改企业
        checkUnifiedSocial(request.getUscc());
        companyInfoEntity.setUscc(request.getUscc());
        companyInfoEntity.setCorpNm(request.getCorpNm());
        companyInfoEntity.setDescribe(request.getDescribe());
        companyInfoEntity.setUpdateTime(new Date());
        companyInfoEntity.setUpdateUserId(userId);
        companyInfoEntity.setUpdateBy(user.getUsername());
        updateById(companyInfoEntity);
        //去除旧标签，同时更新企业标签数量和标签被企业使用数量
        List<CompanyTagEntity> oldCompanyTag = companyTagService.getListByCompanyId(request.getId());
        List<String> oldTagIds = oldCompanyTag.stream().map(old -> old.getTagId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(request.getTagIds())) {
            oldTagIds.removeAll(request.getTagIds());
        }
        for (String tagId : oldTagIds) {
            deleteCompanyTag(user, new Date(), request.getId(), tagId);
        }
        //更新标签
        if (!CollectionUtils.isEmpty(request.getTagIds())) {
            for (String tagId : request.getTagIds()) {
                CompanyTagEntity companyTag = companyTagService.findByTagIdAndCompanyId(tagId, request.getId());
                if (Objects.isNull(companyTag)) {
                    saveCompanyTag(user, new Date(), request.getId(), tagId);
                } else {
                    updateCompanyTag(user, companyTag);
                }
            }
        }
        return true;
    }

    private void deleteCompanyTag(InternalUserInfoVO user, Date date, String companyInfoId, String tagId) {
        CompanyTagEntity companyTagEntity = companyTagService.findByTagIdAndCompanyId(tagId, companyInfoId);
        companyTagEntity.setUpdateTime(date);
        companyTagEntity.setUpdateUserId(user.getUserId());
        companyTagEntity.setUpdateBy(user.getUsername());
        companyTagEntity.setDelFlag(DelFalgStateEnum.HAS_DELETE.getCode());
        companyTagService.updateById(companyTagEntity);
    }

    private void updateCompanyTag(InternalUserInfoVO user, CompanyTagEntity companyTag) {
        companyTag.setUpdateTime(new Date());
        companyTag.setUpdateUserId(user.getUserId());
        companyTag.setUpdateBy(user.getUsername());
        companyTagService.updateById(companyTag);
    }

    @Override
    public CompanyListResult detailById(String id) {
        CompanyListResult result = new CompanyListResult();
        CompanyInfoEntity companyInfo = getById(id);
        if (Objects.isNull(companyInfo)) {
            return result;
        }
        BeanCopyUtils.copyProperties(companyInfo, result);
        List<TagEntity> tagListByCompanyId = tagService.getByCompanyId(id);
        if (!CollectionUtils.isEmpty(tagListByCompanyId)) {
            if (!CollectionUtils.isEmpty(tagListByCompanyId)) {
                List<String> tagIdList = tagListByCompanyId.stream().map(o -> o.getId()).collect(Collectors.toList());
                result.setTagMap(tagIdList);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean uploadImport(String userId, MultipartFile file, String ids) {
        List<String> tadIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)){
            //根据前端传值做匹配
            ids = ids.replaceAll("\\[", "").replaceAll("]", "").replace("\"", "");
            String[] idsArr = ids.split(",");
            tadIdList = Arrays.asList(idsArr);
        }
        List<CompanyInfoImportContent> importList = ExcelUtils.readExcelFileData(file, 1, 1, CompanyInfoImportContent.class);
        for (CompanyInfoImportContent content : importList) {
            saveDef(userId, new CompanySaveRequest(content.getUscc(), content.getCorpNm(), tadIdList, content.getDescribe()));
        }
        return true;
    }

    @Override
    public void updateTagNumById(Long count, String companyId) {
        companyInfoMapper.updateTagNumById(count, companyId);
    }

    private void deleteCompanyTagByCompanyId(InternalUserInfoVO user, List<String> companyIds) {
        companyTagService.deleteCompanyTagByCompanyId(user.getUserId(), companyIds);
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
        if (!CollectionUtils.isEmpty(companyIdList)) {
            wrapper.in(CompanyInfoEntity.ID, companyIdList);
        }
        if (StringUtils.isNotBlank(request.getUpdateTime()) && "desc".equals(request.getUpdateTime())) {
            wrapper.orderByDesc(CompanyInfoEntity.UPDATE_TIME);
        }
        if (StringUtils.isNotBlank(request.getTagNum()) && "desc".equals(request.getTagNum())) {
            wrapper.orderByDesc(CompanyInfoEntity.TAG_NUM);
        }
        return wrapper;
    }


    @NotNull
    private CompanyInfoEntity saveCompanyInfo(InternalUserInfoVO user, CompanySaveRequest request, Date date) {
        checkUnifiedSocial(request.getUscc());
        CompanyInfoEntity companyInfoEntity = BeanCopyUtils.copyProperties(request, new CompanyInfoEntity());
        companyInfoEntity.setCreateTime(date);
        companyInfoEntity.setCreateUserId(user.getUserId());
        companyInfoEntity.setCreateBy(user.getUsername());
        companyInfoEntity.setUpdateTime(date);
        companyInfoEntity.setUpdateUserId(user.getUserId());
        companyInfoEntity.setUpdateBy(user.getUsername());
        companyInfoEntity.setTagNum(request.getTagIds().size());
        save(companyInfoEntity);
        return companyInfoEntity;
    }

    private void checkUnifiedSocial(String uscc) {
        if (!CharUtil.isUnifiedSocial(uscc)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000017.getCode());
        }
    }

    private void saveCompanyTag(InternalUserInfoVO user, Date date, String companyInfoId, String tagId) {
        CompanyTagEntity companyTagEntity = new CompanyTagEntity();
        companyTagEntity.setTagId(tagId);
        companyTagEntity.setCompanyInfoId(companyInfoId);
        companyTagEntity.setCreateTime(date);
        companyTagEntity.setCreateUserId(user.getUserId());
        companyTagEntity.setCreateBy(user.getUsername());
        companyTagEntity.setUpdateTime(date);
        companyTagEntity.setUpdateUserId(user.getUserId());
        companyTagEntity.setUpdateBy(user.getUsername());
        companyTagService.save(companyTagEntity);
        TagEntity tagEntity = tagService.detailById(tagId);
        tagEntity.setLastUsedTime(date);
        tagService.updateById(tagEntity);
    }
}
