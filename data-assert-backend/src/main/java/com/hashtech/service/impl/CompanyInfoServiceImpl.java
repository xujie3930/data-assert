package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.easyexcel.bean.CompanyInfoImportContent;
import com.hashtech.entity.*;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.CompanyInfoMapper;
import com.hashtech.service.*;
import com.hashtech.utils.CharUtil;
import com.hashtech.utils.excel.ExcelUtils;
import com.hashtech.web.request.*;
import com.hashtech.web.result.CompanyDetailResult;
import com.hashtech.web.result.CompanyListResult;
import com.hashtech.web.result.Structure;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.AopContext;
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
    @Autowired
    private IndustrialCompanyService industrialCompanyService;
    @Autowired
    private IndustrialService industrialService;

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDef(String userId, CompanySaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        Date date = new Date();
        CompanyInfoEntity companyInfoEntity = saveCompanyInfo(user, request, date);
        String companyInfoId = companyInfoEntity.getId();
        //保存企业-标签信息
        companyTagService.saveOrUpdateBatchDef(user, date, companyInfoId, request.getTagIds());
        companyInfoEntity.setTagNum(null == request.getTagIds()? 0 : request.getTagIds().size());
        updateById(companyInfoEntity);
        //保存产业-企业库信息
        industrialCompanyService.saveOrUpdateIndustrialCompanyBatch(user, date, companyInfoId, request.getIndustrialIds());
        return true;
    }


    @Override
    public Boolean hasExistUscc(String uscc, List<String> industrialIds) {
        CompanyInfoEntity entity = companyInfoMapper.findByUsccAndCorpNm(uscc, null);
        if (Objects.isNull(entity)){
            return false;
        }
        return industrialCompanyService.hasExistByCompanyIdAndIndustrialIds(entity.getId(), industrialIds);
    }

    @Override
    public BusinessPageResult<CompanyListResult> getList(CompanyListRequest request) {
        List<String> companyIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getTagId())) {
            List<CompanyTagEntity> companyTagList = companyTagService.getLitsByTagId(request.getTagId());
            if (!CollectionUtils.isEmpty(companyTagList)) {
                companyIdList = companyTagList.stream().map(o -> o.getCompanyInfoId()).collect(Collectors.toList());
            }else {
                return BusinessPageResult.build(Collections.emptyList(), request, 0);
            }
        }
        if (StringUtils.isNotBlank(request.getIndustrialName())) {
            List<IndustrialEntity> industrialList = industrialService.likeByName(request.getIndustrialName());
            //获取所有产业id
            List<String> industryIdList = industrialList.stream().map(IndustrialEntity::getId).collect(Collectors.toList());
            List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByIndustrialIds(industryIdList);
            if (!CollectionUtils.isEmpty(industrialCompanyList)) {
                //取两个集合交集
                List<String> collect = industrialCompanyList.stream().map(o -> o.getCompanyInfoId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(companyIdList)){
                    collect.retainAll(companyIdList);
                }
                companyIdList = collect;
            }else {
                return BusinessPageResult.build(Collections.emptyList(), request, 0);
            }
        }
        if (StringUtils.isNotBlank(request.getIndustrialId())) {
            List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByIndustrialId(request.getIndustrialId());
            if (!CollectionUtils.isEmpty(industrialCompanyList)) {
                //取两个集合交集
                List<String> collect = industrialCompanyList.stream().map(o -> o.getCompanyInfoId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(companyIdList)){
                    collect.retainAll(companyIdList);
                }
                companyIdList = collect;
            }else {
                return BusinessPageResult.build(Collections.emptyList(), request, 0);
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
                List<Map<String, String>> list = new LinkedList<>();
                for (TagEntity tagEntity : tagListByCompanyId) {
                    Map<String, String> map = new HashMap<>();
                    map.put("tagId", tagEntity.getId());
                    map.put("tagName", tagEntity.getName());
                    list.add(map);
                }
                companyListResult.setTagMap(list);
            }
            companyListResult.setIndustrialId(request.getIndustrialId());
            companyListResults.add(companyListResult);
        }
        resultIPage.setRecords(companyListResults);
        resultIPage.setPages(page.getPages());
        resultIPage.setTotal(page.getTotal());
        return BusinessPageResult.build(resultIPage, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCompanyDef(String userId, Map<String, String[]> request) {
        if (CollectionUtils.isEmpty(request)){
            return true;
        }
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        //变更资源表状态
//        deleteCompanyTagByCompanyId(user, Arrays.asList(ids));
//        deleteCompany(user, ids);
        //删除该产业下的企业
        for (Map.Entry<String, String[]> entry : request.entrySet()) {
            String industrialId = entry.getKey();
            String[] companyIdList = entry.getValue();
            Set<String> companyIdSet = new HashSet<>(Arrays.asList(companyIdList));
            //该产业下的所有企业
            List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByIndustrialId(industrialId);
            industrialCompanyList = industrialCompanyList.stream().filter(i -> companyIdSet.contains(i.getCompanyInfoId())).collect(Collectors.toList());
            industrialCompanyService.removeByIds(industrialCompanyList.stream().map(IndustrialCompanyEntity::getId).collect(Collectors.toList()));
        }
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
        CompanyInfoEntity companyInfoEntity = companyInfoMapper.findById(request.getId());
        if (Objects.isNull(companyInfoEntity)) {
            return true;
        }
        checkUnifiedSocial(request.getUscc());
        //去除旧标签，同时更新企业标签数量和标签被企业使用数量
        companyTagService.saveOrUpdateBatchDef(user,new Date(), companyInfoEntity.getId(), request.getTagIds());
        //更新产业-企业信息
        industrialCompanyService.saveOrUpdateIndustrialCompanyBatch(user, new Date(), companyInfoEntity.getId(), request.getIndustrialIds());
        ////更改企业
        companyInfoEntity.setTagNum(null == request.getTagIds()? 0 : request.getTagIds().size());
        companyInfoEntity.setUscc(request.getUscc());
        companyInfoEntity.setCorpNm(request.getCorpNm());
        companyInfoEntity.setDescribe(request.getDescribe());
        companyInfoEntity.setUpdateTime(new Date());
        companyInfoEntity.setUpdateUserId(userId);
        companyInfoEntity.setUpdateBy(user.getUsername());
        updateById(companyInfoEntity);
        return true;
    }

    @Override
    public CompanyDetailResult detailById(String id) {
        CompanyDetailResult result = new CompanyDetailResult();
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
        List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByCompanyId(id);
        if (!CollectionUtils.isEmpty(industrialCompanyList)) {
            List<String> industrialList = industrialCompanyList.stream().map(o -> o.getIndustrialId()).collect(Collectors.toList());
            result.setIndustrialIds(industrialList);
        }
        return result;
    }


    @Override
    public BusinessPageResult getRelateList(CompanyListRequest request) {
        List<CompanyTagEntity> companyTagList = companyTagService.getLitsByTagId(request.getTagId());
        if (CollectionUtils.isEmpty(companyTagList)) {
            return BusinessPageResult.build(Collections.emptyList(), request, 0);
        }
        return getList(request);
    }

    @Override
    public List<String> getCompanyIdList(IndustryListRequest request) {
        return companyInfoMapper.getCompanyIdList(request);
    }

    private void deleteCompanyTagByCompanyId(InternalUserInfoVO user, List<String> companyIds) {
        for (String companyId : companyIds) {
            List<CompanyTagEntity> companyTagList = companyTagService.getListByCompanyId(companyId);
            if (CollectionUtils.isEmpty(companyTagList)){
                return;
            }
            List<String> tagIds = companyTagList.stream().map(old -> old.getTagId()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tagIds)){
                return;
            }
            companyTagService.deleteCompanyTagBatch(user, new Date(), companyId, tagIds);
        }
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
        //根据统一社会信用代码和企业名称确认是否需要新增
        CompanyInfoEntity entity= companyInfoMapper.findByUsccAndCorpNm(request.getUscc(), null);
        if (!Objects.isNull(entity)) {
            //判断该企业在选定的产业库下是否存在，不存在，则新增
            if (industrialCompanyService.hasExistByCompanyIdAndIndustrialIds(entity.getId(), request.getIndustrialIds())) {
                throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000016.getCode());
            }
            updateCompanyInfo(user, request, date, entity);
            return entity;
        }

        checkUnifiedSocial(request.getUscc());
        CompanyInfoEntity companyInfoEntity = BeanCopyUtils.copyProperties(request, new CompanyInfoEntity());
        companyInfoEntity.setCreateTime(date);
        companyInfoEntity.setCreateUserId(user.getUserId());
        companyInfoEntity.setCreateBy(user.getUsername());
        companyInfoEntity.setUpdateTime(date);
        companyInfoEntity.setUpdateUserId(user.getUserId());
        companyInfoEntity.setUpdateBy(user.getUsername());
        save(companyInfoEntity);
        return companyInfoEntity;
    }

    private void updateCompanyInfo(InternalUserInfoVO user, CompanySaveRequest request, Date date, CompanyInfoEntity entity) {
        BeanCopyUtils.copyProperties(request, entity);
        entity.setUpdateTime(date);
        entity.setUpdateUserId(user.getUserId());
        entity.setUpdateBy(user.getUsername());
        updateById(entity);
    }

    private void checkUnifiedSocial(String uscc) {
        if (!CharUtil.isUnifiedSocial(uscc)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000017.getCode());
        }
    }
}
