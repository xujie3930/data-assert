package com.hashtech.service.impl;

import com.hashtech.entity.IndustrialCompanyEntity;
import com.hashtech.mapper.IndustrialCompanyMapper;
import com.hashtech.service.IndustrialCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
