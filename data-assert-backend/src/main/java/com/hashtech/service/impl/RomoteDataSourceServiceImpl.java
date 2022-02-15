package com.hashtech.service.impl;

import com.hashtech.common.BusinessResult;
import com.hashtech.feign.DatasourceFeignClient;
import com.hashtech.service.RomoteDataSourceService;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 获取远程数据源信息
 * @create 2022-02-14 15:00
 **/
@Service
public class RomoteDataSourceServiceImpl implements RomoteDataSourceService {
    @Autowired
    private DatasourceFeignClient datasourceFeignClient;
    @Override
    public BusinessResult<List<Map<String, Object>>> getDataSourcesList(DataSourcesListRequest request) {
        return datasourceFeignClient.getDataSourcesList(request);
    }

    @Override
    public BusinessResult<List<Map<String, String>>> getTableNameList(TableNameListRequest request) {
        return datasourceFeignClient.getTableNameList(request);
    }
}
