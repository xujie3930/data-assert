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
import com.hashtech.mapper.IndustrialCompanyMapper;
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
    @Autowired
    private IndustrialCompanyMapper industrialCompanyMapper;

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
        industrialCompanyService.saveOrUpdateIndustrialCompanyBatch(user, date, companyInfoId, request.getIndustrialIds(),InterfaceTypeEnum.SAVE.getCode());
        return true;
    }


    public void checkExistUscc(String uscc, List<String> industrialIds, String companyInfoId) {
        CompanyInfoEntity entity = companyInfoMapper.findByUsccAndCorpNm(uscc, null, companyInfoId);
        if (!Objects.isNull(entity)){
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000029.getCode());
        }
        List<String> industrialIdList = industrialCompanyMapper.hasExistByCompanyIdAndIndustrialIds(companyInfoId, industrialIds);
        if (CollectionUtils.isEmpty(industrialIdList)){
            return;
        }
//        List<IndustrialEntity> industrialEntityList = industrialService.listByIds(industrialIdList);
//        List<String> industryNameList = industrialEntityList.stream().map(IndustrialEntity::getName).collect(Collectors.toList());
//        CompanyInfoEntity companyInfoEntity = getById(companyInfoId);
//        String industryName = StringUtils.join(industryNameList, ",");
//        String errMsg = industryName + "产业库下已存在 " + companyInfoEntity.getCorpNm() + " 企业信息";
//        throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000016.getCode(), errMsg);
    }

    @Override
    public BusinessPageResult<CompanyListResult> getList(CompanyListRequest request) {
        Long count = selectCountByRequest(request);
        if(null==count || count.longValue()<=0l){
            return BusinessPageResult.build(new ArrayList<>(0), request, count);
        }
        List<CompanyInfoEntity> records = selectByRequest(request);
        if(null==records || records.isEmpty()){
            return BusinessPageResult.build(new ArrayList<>(0), request, count);
        }
        Set<String> ids = records.stream().map(CompanyInfoEntity::getId).collect(Collectors.toSet());
        List<Map<String, Object>> tags = tagService.getByCompanyIds(ids);
        Map<String, List<Map<String, Object>>> tagMap = new HashMap<>();
        if(null!=tags && !tags.isEmpty()){
            tags.stream().forEach(tag->{
                String companyInfoId = (String) tag.remove("companyInfoId");
                if(!org.springframework.util.StringUtils.isEmpty(companyInfoId)){
                    List<Map<String, Object>> tagEntities = tagMap.get(companyInfoId);
                    if(null==tagEntities){
                        tagEntities = new ArrayList<>(5);
                        tagMap.put(companyInfoId, tagEntities);
                    }
                    tagEntities.add(tag);
                }
            });
        }
        List<CompanyListResult> companyListResults = new LinkedList<>();
        for (CompanyInfoEntity record : records) {
            CompanyListResult companyListResult = BeanCopyUtils.copyProperties(record, new CompanyListResult());
            List<Map<String, Object>> tagListByCompanyId = tagMap.get(record.getId());
            if (!CollectionUtils.isEmpty(tagListByCompanyId)) {
                List<Map<String, String>> list = new LinkedList<>();
                for (Map<String, Object> tagEntity : tagListByCompanyId) {
                    Map<String, String> map = new HashMap<>();
                    map.put("tagId", (String) tagEntity.get("id"));
                    map.put("tagName", (String) tagEntity.get("name"));
                    list.add(map);
                }
                companyListResult.setTagMap(list);
            }
            /*List<TagEntity> tagListByCompanyId = tagService.getByCompanyId(record.getId());
            if (!CollectionUtils.isEmpty(tagListByCompanyId)) {
                List<Map<String, String>> list = new LinkedList<>();
                for (TagEntity tagEntity : tagListByCompanyId) {
                    Map<String, String> map = new HashMap<>();
                    map.put("tagId", tagEntity.getId());
                    map.put("tagName", tagEntity.getName());
                    list.add(map);
                }
                companyListResult.setTagMap(list);
            }*/
            companyListResult.setIndustrialId(request.getIndustrialId());
            companyListResults.add(companyListResult);
        }
        return BusinessPageResult.build(companyListResults, request, count);
    }

    private Long selectCountByRequest(CompanyListRequest request) {
        String industrialId = request.getIndustrialId();
        if("0".equals(industrialId)){
            QueryWrapper<IndustrialEntity> query = new QueryWrapper<>();
            query.eq("del_flag", 0);
            String industrialName = request.getIndustrialName();
            List<IndustrialEntity> list = industrialService.list(query);
            if(null==list || list.isEmpty() || list.size()<=1){
                //小于等于1个时，只有默认分组，就不需要关联表
                request.setIndustrialId(null);


                if(!org.springframework.util.StringUtils.isEmpty(industrialName) && list.size()==1 && !org.springframework.util.StringUtils.isEmpty(list.get(0).getName()) && !list.get(0).getName().contains(industrialName)){
                    //只有一个产业库时，直接在此匹配
                    return 0l;
                }else{
                    //只有一个产业库，且匹配成功，则查询全部企业
                    request.setIndustrialName(null);
                }
            }else if(!org.springframework.util.StringUtils.isEmpty(industrialName)){
                //多个
                boolean run = false;
                for(IndustrialEntity entity:list){
                    if(!org.springframework.util.StringUtils.isEmpty(entity.getName()) && entity.getName().contains(industrialName)){
                        run=true;
                        break;
                    }
                }
                if(!run){
                    return 0l;
                }
            }
        }
        return companyInfoMapper.selectCountByRequest(request);
    }

    private List<CompanyInfoEntity> selectByRequest(CompanyListRequest request) {
        return companyInfoMapper.selectByRequest(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCompanyDef(String userId, Map<String, String[]> request) {
        if (CollectionUtils.isEmpty(request)){
            return true;
        }
        List<String> removeCompanyIds = new ArrayList<>();
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        //删除该产业下的企业
        for (Map.Entry<String, String[]> entry : request.entrySet()) {
            String industrialId = entry.getKey();
            String[] companyIdList = entry.getValue();
            Set<String> companyIdSet = new HashSet<>(Arrays.asList(companyIdList));
            //该产业下的所有企业
            List<IndustrialCompanyEntity> industrialCompanyList = industrialCompanyService.selectByIndustrialId(industrialId);
            industrialCompanyList = industrialCompanyList.stream().filter(i -> companyIdSet.contains(i.getCompanyInfoId())).collect(Collectors.toList());
            industrialCompanyService.removeByIds(industrialCompanyList.stream().map(IndustrialCompanyEntity::getId).collect(Collectors.toList()));
            for (String companyId : companyIdList) {
                List<IndustrialCompanyEntity> industrialListByCompany = industrialCompanyService.selectByCompanyId(companyId);
                if (CollectionUtils.isEmpty(industrialListByCompany)){
                    removeCompanyIds.add(companyId);
                }
            }
        }
        //删除企业及其标签
        if (!CollectionUtils.isEmpty(removeCompanyIds)){
            String[] removeArr = removeCompanyIds.stream().toArray(String[]::new);
            deleteCompanyTagByCompanyId(user, removeCompanyIds);
            deleteCompany(user, removeArr);
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
        checkExistUscc(request.getUscc(), request.getIndustrialIds(), request.getId());
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        CompanyInfoEntity companyInfoEntity = companyInfoMapper.findById(request.getId());
        if (Objects.isNull(companyInfoEntity)) {
            return true;
        }
        checkUnifiedSocial(request.getUscc());
        //去除旧标签，同时更新企业标签数量和标签被企业使用数量
        companyTagService.saveOrUpdateBatchDef(user,new Date(), companyInfoEntity.getId(), request.getTagIds());
        //更新产业-企业信息
        industrialCompanyService.saveOrUpdateIndustrialCompanyBatch(user, new Date(), companyInfoEntity.getId(), request.getIndustrialIds(), InterfaceTypeEnum.UPDATE.getCode());
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
        CompanyInfoEntity entity= companyInfoMapper.findByUsccAndCorpNm(request.getUscc(), null, null);
        if (!Objects.isNull(entity)) {
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
