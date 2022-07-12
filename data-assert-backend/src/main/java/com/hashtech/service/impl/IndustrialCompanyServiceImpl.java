package com.hashtech.service.impl;

import com.hashtech.common.DelFlagEnum;
import com.hashtech.entity.IndustrialCompanyEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.IndustrialCompanyMapper;
import com.hashtech.service.IndustrialCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
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
    public void saveOrUpdateIndustrialCompanyBatch(InternalUserInfoVO user, Date date, String companyInfoId, List<String> industrialIds) {
        //更新现有产业
        List<IndustrialCompanyEntity> saveList = new ArrayList<>();
        List<IndustrialCompanyEntity> industrialCompanyList = selectByCompanyId(companyInfoId);
        List<String> allIndustrialIds = industrialCompanyList.stream().map(IndustrialCompanyEntity::getIndustrialId).collect(Collectors.toList());
        for (String industrialId : industrialIds) {
            addEntityToSaveList(user, date, companyInfoId, saveList, industrialCompanyList, industrialId);
        }
        saveOrUpdateBatch(saveList);
        //删除原有产业
        allIndustrialIds.retainAll(industrialIds);
        if (!CollectionUtils.isEmpty(allIndustrialIds)) {
            List<IndustrialCompanyEntity> removeList = new ArrayList<>();
            for (String removeIndustrialId : allIndustrialIds) {
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
        return industrialCompanyMapper.selectByCompanyIds(companyIdList);
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
