package com.hashtech.service.impl;

import com.hashtech.entity.CompanyTagEntity;
import com.hashtech.mapper.CompanyTagMapper;
import com.hashtech.service.CompanyTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
