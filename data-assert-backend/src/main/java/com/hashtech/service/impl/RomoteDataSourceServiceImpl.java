package com.hashtech.service.impl;

import com.hashtech.common.AppException;
import com.hashtech.common.BusinessResult;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.feign.DatasourceFeignClient;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.service.RomoteDataSourceService;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public DatasourceDetailResult getDatasourceDetail(String datasourceId) {
        if (StringUtils.isBlank(datasourceId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000037.getCode());
        }
        BusinessResult<DatasourceDetailResult> datasourceResult = datasourceFeignClient.info(datasourceId);
        if (!datasourceResult.isSuccess() || Objects.isNull(datasourceResult.getData())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.getCode());
        }
        return datasourceResult.getData();
    }
}
