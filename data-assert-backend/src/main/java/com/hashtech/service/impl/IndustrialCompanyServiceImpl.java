package com.hashtech.service.impl;

import com.hashtech.entity.IndustrialCompanyEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.IndustrialCompanyMapper;
import com.hashtech.service.IndustrialCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        List<IndustrialCompanyEntity> list = new ArrayList<>();
        List<IndustrialCompanyEntity> industrialCompanyList = selectByCompanyId(companyInfoId);
        for (String industrialId : industrialIds) {
            IndustrialCompanyEntity industrialCompanyEntity = industrialCompanyList.stream().filter(i -> industrialId.equals(i.getIndustrialId())).findFirst().get();
            if (Objects.isNull(industrialCompanyEntity)){
                industrialCompanyEntity = new IndustrialCompanyEntity();
                industrialCompanyEntity.setCreateTime(date);
                industrialCompanyEntity.setCreateUserId(user.getUserId());
                industrialCompanyEntity.setCreateBy(user.getUsername());
            }
            industrialCompanyEntity.setIndustrialId(industrialId);
            industrialCompanyEntity.setCompanyInfoId(companyInfoId);
            industrialCompanyEntity.setUpdateTime(date);
            industrialCompanyEntity.setUpdateUserId(user.getUserId());
            industrialCompanyEntity.setUpdateBy(user.getUsername());
            list.add(industrialCompanyEntity);
        }
        saveOrUpdateBatch(list);
    }
}
