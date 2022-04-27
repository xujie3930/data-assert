package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.mapper.CompanyTagMapper;
import com.hashtech.service.CompanyTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}
