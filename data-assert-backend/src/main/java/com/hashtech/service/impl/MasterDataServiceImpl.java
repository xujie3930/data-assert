package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.MasterDataEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.mapper.MasterDataMapper;
import com.hashtech.service.MasterDataService;
import com.hashtech.service.ThemeResourceService;
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
    @Autowired
    private ThemeResourceService themeResourceService;

    @Override
    public BusinessResult<List<MasterDataEntity>> getDataSource() {
        List<MasterDataEntity> list = masterDataMapper.getList();
        //TODO:主数据功能暂时先这样改，后续会移出去单独功能
        for (MasterDataEntity masterDataEntity : list) {
            ThemeResourceEntity themeResourceEntity = themeResourceService.getById(masterDataEntity.getThemeId());
            masterDataEntity.setName(themeResourceEntity.getName());
        }
        return BusinessResult.success(list);
    }
}
