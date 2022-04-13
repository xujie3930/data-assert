package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.MasterDataEntity;
import com.hashtech.mapper.MasterDataMapper;
import com.hashtech.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主数据类别表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-03-23
 */
@Service
public class MasterDataServiceImpl extends ServiceImpl<MasterDataMapper, MasterDataEntity> implements MasterDataService {

    @Autowired
    private MasterDataMapper masterDataMapper;

    @Override
    public BusinessResult<List<Map<Integer, String>>> getDataSource() {
        return BusinessResult.success(masterDataMapper.getList());
    }
}
