package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.AppException;
import com.hashtech.common.DelFlagEnum;
import com.hashtech.common.InterfaceTypeEnum;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.entity.IndustrialCompanyEntity;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.IndustrialCompanyMapper;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.IndustrialCompanyService;
import com.hashtech.service.IndustrialService;
import com.hashtech.web.request.IndustryListRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 产业-企业表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
@Service
public class IndustrialCompanyServiceImpl extends ServiceImpl<IndustrialCompanyMapper, IndustrialCompanyEntity> implements IndustrialCompanyService {

    @Autowired
    private IndustrialCompanyMapper industrialCompanyMapper;
    @Autowired
    private IndustrialService industrialService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    public List<IndustrialCompanyEntity> selectByIndustrialId(String id) {
        return industrialCompanyMapper.selectByIndustrialId(id);
    }

    @Override
    public List<IndustrialCompanyEntity> selectByIndustrialIds(List<String> ids) {
        return industrialCompanyMapper.selectByIndustrialIds(ids);
    }

    @Override
    public IndustrialCompanyEntity selectByIndustrialAndCompanyId(String industrialId, String companyInfoId) {
        return industrialCompanyMapper.selectByIndustrialAndCompanyId(industrialId, companyInfoId);
    }

    @Override
    public List<IndustrialCompanyEntity> selectByCompanyId(String companyInfoId) {
        return industrialCompanyMapper.selectByCompanyId(companyInfoId);
    }

    @Override
    public void saveOrUpdateIndustrialCompanyBatch(InternalUserInfoVO user, Date date, String companyInfoId, List<String> industrialIds, Integer interfaceType) {
        List<IndustrialCompanyEntity> industrialCompanyList = selectByCompanyId(companyInfoId);
        List<String> allIndustrialIds = industrialCompanyList.stream().map(IndustrialCompanyEntity::getIndustrialId).collect(Collectors.toList());
        //判断现有产业id是否与传参完全一致
        if (InterfaceTypeEnum.SAVE.getCode().equals(interfaceType)){
            allIndustrialIds.sort(Comparator.comparing(String::hashCode));
            industrialIds.sort(Comparator.comparing(String::hashCode));
            if (allIndustrialIds.toString().equals(industrialIds.toString()) || allIndustrialIds.containsAll(industrialIds)){
                Collection<IndustrialEntity> industrialEntityList = industrialService.listByIds(industrialIds);
                List<String> industryNameList = industrialEntityList.stream().map(IndustrialEntity::getName).collect(Collectors.toList());
                String industryName = StringUtils.join(industryNameList, "、");
                String errMsg = "在" + industryName + "下企业信息已重复，请重新输入";
                throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000016.getCode(), errMsg);
            }
        }
        //更新现有产业
        List<IndustrialCompanyEntity> saveList = new ArrayList<>();
        for (String industrialId : industrialIds) {
            addEntityToSaveList(user, date, companyInfoId, saveList, industrialCompanyList, industrialId);
        }
        saveOrUpdateBatch(saveList);
        //删除原有产业
        List<String> removeIds = ListUtils.subtract(allIndustrialIds, industrialIds);
        if (!CollectionUtils.isEmpty(removeIds)) {
            List<IndustrialCompanyEntity> removeList = new ArrayList<>();
            for (String removeIndustrialId : removeIds) {
                IndustrialCompanyEntity removeEntity = industrialCompanyList.stream().filter(i -> removeIndustrialId.equals(i.getIndustrialId())).findFirst().get();
                removeEntity.setUpdateTime(date);
                removeEntity.setUpdateUserId(user.getUserId());
                removeEntity.setUpdateBy(user.getUsername());
                removeEntity.setDelFlag(DelFlagEnum.DIS_ABLED.getCode());
                removeList.add(removeEntity);
            }
            saveOrUpdateBatch(removeList);
            removeByIds(removeList.stream().map(i -> i.getId()).collect(Collectors.toList()));
        }

    }

    @Override
    public List<IndustrialCompanyEntity> selectByCompanyIds(List<String> companyIdList) {
        if (CollectionUtils.isEmpty(companyIdList)){
            return null;
        }
        return industrialCompanyMapper.selectByCompanyIds(companyIdList);
    }

    @Override
    public Boolean hasExistByCompanyIdAndIndustrialIds(String id, List<String> industrialIds) {
        List<String> industrialIdList = industrialCompanyMapper.hasExistByCompanyIdAndIndustrialIds(id, industrialIds);
        return CollectionUtils.isNotEmpty(industrialIdList);
    }

    @Override
    public List<IndustrialEntity> selectByRequest(IndustryListRequest request) {
        return industrialCompanyMapper.selectByRequest(request);
    }

    private void addEntityToSaveList(InternalUserInfoVO user, Date date, String companyInfoId, List<IndustrialCompanyEntity> saveList, List<IndustrialCompanyEntity> industrialCompanyList, String industrialId) {
        IndustrialCompanyEntity industrialCompanyEntity;
        Optional<IndustrialCompanyEntity> optional = industrialCompanyList.stream().filter(i -> industrialId.equals(i.getIndustrialId())).findFirst();
        if (!optional.isPresent()){
            industrialCompanyEntity = new IndustrialCompanyEntity();
            industrialCompanyEntity.setCreateTime(date);
            industrialCompanyEntity.setCreateUserId(user.getUserId());
            industrialCompanyEntity.setCreateBy(user.getUsername());
        }else {
            industrialCompanyEntity = optional.get();
        }
        industrialCompanyEntity.setIndustrialId(industrialId);
        industrialCompanyEntity.setCompanyInfoId(companyInfoId);
        industrialCompanyEntity.setUpdateTime(date);
        industrialCompanyEntity.setUpdateUserId(user.getUserId());
        industrialCompanyEntity.setUpdateBy(user.getUsername());
        saveList.add(industrialCompanyEntity);
    }
}
