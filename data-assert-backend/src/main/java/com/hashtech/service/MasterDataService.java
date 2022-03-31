package com.hashtech.service;

import com.hashtech.common.BusinessResult;
import com.hashtech.entity.MasterDataEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主数据类别表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-03-23
 */
public interface MasterDataService extends IService<MasterDataEntity> {

    BusinessResult<List<Map<Integer, String>>> getDataSource();
}
