package com.hashtech.service;

import com.hashtech.common.BusinessResult;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-12-01
 */
public interface RomoteDataSourceService {

    BusinessResult<List<Map<String, Object>>> getDataSourcesList(DataSourcesListRequest request);

    BusinessResult<List<Map<String, String>>> getTableNameList(TableNameListRequest request);

    DatasourceDetailResult getDatasourceDetail(String datasourceId);
}
